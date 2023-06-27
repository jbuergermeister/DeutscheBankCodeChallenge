package com.fdmgroup.api.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.api.model.Company;
import com.fdmgroup.api.model.Statistic;
import com.fdmgroup.api.repository.CompanyRepository;
import com.fdmgroup.api.repository.StatisticRepository;

/**
 * <p>
 * Service that provides basic CRUD functionality as well as Index calculation and
 * retrieval.
 * </p>
 * 
 * @author Jakob Buergermeister
 *
 */
@Service
public class StatisticService {
	
	@Autowired
	private StatisticRepository statisticRepo;
	
	@Autowired
	private CompanyRepository companyRepo;
	
	public Statistic getStatisticByDateAndTicker(LocalDate date, String ticker) {
		List<Statistic> statList = statisticRepo.findByDateAndTicker(date, ticker);
		if (statList.size() > 0) {
			return statList.get(0);
		}
		return null;
	}
	
	public Statistic getIndexStatisticByDate(LocalDate date) {
		List<Company> companies = companyRepo.findAll();
		LocalDate earliestDate = statisticRepo.getEarliestDate();
		System.out.println(earliestDate.toString());
		double indexOpen = 0.;
		double indexClose = 0.;
		double indexHigh = 0.;
		double indexLow = 0.;
		double indexVolume = 0.;
		for(Company company : companies) {
			Statistic companyStatistic = getStatisticByDateAndTicker(date, company.getCompanyTicker());
			if(companyStatistic==null) {
				return null;
			}
			LocalDate latestDate = date;
			double tradedVolume = companyStatistic.getTradedVolume();
			while(tradedVolume < 1.) {
				latestDate = latestDate.minusDays(1L);
				if(date.isBefore(earliestDate)) {
					return null;
				}
				companyStatistic = getStatisticByDateAndTicker(latestDate, company.getCompanyTicker());
				if(companyStatistic==null) {
					tradedVolume = 0.;
				} else {
					tradedVolume = companyStatistic.getTradedVolume();
				}
			}
			double weight = company.getIndexWeight();
			indexOpen += weight * companyStatistic.getOpenPrice();
			indexClose += weight * companyStatistic.getClosePrice();
			indexHigh += weight * companyStatistic.getHighestPrice();
			indexLow += weight * companyStatistic.getLowestPrice();
			indexVolume += weight * companyStatistic.getTradedVolume();
		}
		return new Statistic(new Company("INDEX"), date,
				indexOpen, indexClose, indexHigh, indexLow, indexVolume);
	}

	public void save(Statistic statistic) {
		statisticRepo.save(statistic);
	}

	public List<Statistic> getAllStatistics() {
		return statisticRepo.findAll();
	}

}
