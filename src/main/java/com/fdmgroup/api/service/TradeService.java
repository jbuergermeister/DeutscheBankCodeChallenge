package com.fdmgroup.api.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.api.model.Trade;
import com.fdmgroup.api.repository.TradeRepository;

@Service
public class TradeService {
	
	@Autowired
	private TradeRepository tradeRepo;
	
	public Trade saveTrade(Trade trade) {
		return tradeRepo.save(trade);
	}
	
	public List<Trade> getDailyTickerTrades(LocalDate date, String ticker) {
		return tradeRepo.getTradesByDateAndTicker(date, ticker);
	}

	public List<LocalDate> getDates() {
		return tradeRepo.getDates();
	}

	public List<Trade> getDailyTrades(LocalDate day) {
		return tradeRepo.getDailyTrades(day);
	}

	public List<Trade> getAllTrades() {
		return tradeRepo.findAll();
	}

}
