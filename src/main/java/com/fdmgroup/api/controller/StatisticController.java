package com.fdmgroup.api.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.api.model.Statistic;
import com.fdmgroup.api.service.StatisticService;

/**
 * <p>
 * Controller for API end points to perform CRUD operations on Statistics in the database.
 * </p>
 * 
 * @author Jakob Buergermeister
 *
 */
@RestController
@RequestMapping("/api/v1/statistic")
public class StatisticController {
	
	@Autowired
	private StatisticService statisticService;
	
	@GetMapping
	public List<Statistic> getAllStatistics() {
		return statisticService.getAllStatistics();
	}

	@GetMapping("/date/{date}/index")
	public Statistic getIndexByDate(@PathVariable LocalDate date) {
		return statisticService.getIndexStatisticByDate(date);
	}
	
	@GetMapping("/date/{date}/ticker/{ticker}")
	public Statistic getStatisticByDateAndTicker(@PathVariable LocalDate date, @PathVariable String ticker) {
		return statisticService.getStatisticByDateAndTicker(date, ticker);
	}
}
