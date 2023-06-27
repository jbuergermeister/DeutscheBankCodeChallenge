package com.fdmgroup.api;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fdmgroup.api.model.Company;
import com.fdmgroup.api.model.Statistic;
import com.fdmgroup.api.model.Trade;
import com.fdmgroup.api.service.CompanyService;
import com.fdmgroup.api.service.StatisticService;
import com.fdmgroup.api.service.TradeService;

import jakarta.annotation.PostConstruct;

/**
 * <p>
 * Script to populate database and print statistics based on the initial API state. The method setupDB() calls further setup
 * methods for trades and statistics as well as the print method.
 * </p>
 * 
 * @author Jakob Buergermeister
 *
 */
@Component
public class Setup {

	public Setup() {
	}

	@Autowired
	TradeService tradeService;

	@Autowired
	CompanyService companyService;

	@Autowired
	StatisticService statisticService;

	@PostConstruct
	private void setupDB() {
		setupTrades();
		setupStatistics();
		printStatistics();
	}
	
	private void printStatistics() {
		List<Statistic> statistics = statisticService.getAllStatistics();
		int indexPeriod = companyService.findAll().size();
		int counter = 0;
		for(Statistic stat : statistics) {
			counter +=1;
			if(stat.getTradedVolume() > 0) {
				System.out.println(stat.toString());
			} else {
				System.out.println("~~~~~~~~~~ " + stat.getCompany().getCompanyTicker() + " ~~~~~~~~~~\n Date: " + stat.getDate().toString() + "\n Open price: " + "N/A" + "\n Close price: "
						+ "N/A" + "\n Highest price: " + "N/A" + "\n Lowest price: " + "N/A" + "\n Traded volume: "
						+ "N/A" + "\n--------------------------");
			}
			if(counter%indexPeriod==0) {
				Statistic indexStat = statisticService.getIndexStatisticByDate(stat.getDate());
				System.out.println(indexStat);
			}
		}
	}

	private void setupStatistics() {
		List<Company> companies = companyService.findAll();
		List<LocalDate> dates = tradeService.getDates();
		for (LocalDate date : dates) {
			System.out.println(date.toString());
			for (Company company : companies) {
				String ticker = company.getCompanyTicker();
				List<Trade> trades;

					trades = tradeService.getDailyTickerTrades(date, ticker);

				if (trades.size() == 0) {
					Statistic emptyStat = new Statistic();
					emptyStat.setCompany(company);
					emptyStat.setDate(date);
					statisticService.save(emptyStat);
				} else {
					Stream<Trade> tradesStream = trades.stream();
					double openPrice = tradesStream.findFirst().get().getPrice();
					tradesStream = trades.stream();
					double closePrice = tradesStream.reduce((first, second) -> second).get().getPrice();
					tradesStream = trades.stream();
					double highestPrice = tradesStream.map(trade -> trade.getPrice()).max(Comparator.naturalOrder())
							.get();
					tradesStream = trades.stream();
					double lowestPrice = tradesStream.map(trade -> trade.getPrice()).min(Comparator.naturalOrder())
							.get();
					tradesStream = trades.stream();
					double tradedVolume = tradesStream.map(trade -> trade.getPrice() * trade.getNumberTraded())
							.reduce(0., (first, second) -> first + second);
					System.out.println(company.getCompanyTicker() + " - open: " + openPrice + ", high: " + highestPrice
							+ ", volume: " + tradedVolume);
					statisticService.save(new Statistic(company, date, openPrice, closePrice, highestPrice, lowestPrice,
							tradedVolume));

				}

			}
		}
	}

	private void setupTrades() {
		List<Trade> trades = readTradeCSV("src/main/resources/test-market.csv");
		for (Trade trade : trades) {
			Company company = trade.getCompany();
			if (company.getCompanyTicker().equals("ABC")) {
				company.setIndexWeight(0.1);
			} else if (company.getCompanyTicker().equals("MEGA")) {
				company.setIndexWeight(0.3);
			} else if (company.getCompanyTicker().equals("NGL")) {
				company.setIndexWeight(0.4);
			} else if (company.getCompanyTicker().equals("TRX")) {
				company.setIndexWeight(0.2);
			}

			companyService.addCompany(trade.getCompany());
			tradeService.saveTrade(trade);
		}
	}

	private List<Trade> readTradeCSV(String filePath) {
		List<Trade> trades = new ArrayList<>();
		List<Company> companies = new ArrayList<>();
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String[] values = line.split(";");
				LocalDateTime time = LocalDateTime.parse(values[0], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

				Company company = new Company();
				for (Company listedCompany : companies) {
					if (listedCompany.getCompanyTicker().equals(values[1])) {
						company = listedCompany;
					}
				}
				if (company.getCompanyTicker().equals("blank")) {
					company = new Company(values[1]);
				}

				companies.add(company);

				double price = Double.parseDouble(values[2].replace(",", "."));
				long numberTraded = Long.parseLong(values[3]);
				trades.add(new Trade(time, company, price, numberTraded));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return trades;
	}

}
