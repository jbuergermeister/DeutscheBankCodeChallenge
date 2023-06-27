package com.fdmgroup.api.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.api.model.Trade;
import com.fdmgroup.api.service.TradeService;

@RestController
@RequestMapping("/api/v1/trade")
public class TradeController {
	
	@Autowired
	private TradeService tradeService;
	
	@GetMapping
	public List<Trade> getAllTrades() {
		return tradeService.getAllTrades();
	}
	
	@GetMapping("/date/{date}")
	public ResponseEntity<List<Trade>> getDailyTrades(@PathVariable LocalDate date) {
		List<Trade> dailyTrades = tradeService.getDailyTrades(date);
		
		if(dailyTrades.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(dailyTrades);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@GetMapping("/date/{date}/{ticker}")
	public ResponseEntity<List<Trade>> getDailyTickerTrades(@PathVariable LocalDate date, @PathVariable String ticker) {
		List<Trade> dailyTrades = tradeService.getDailyTickerTrades(date, ticker);
		
		if(dailyTrades.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(dailyTrades);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

}
