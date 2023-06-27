package com.fdmgroup.api.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.fdmgroup.api.model.Company;
import com.fdmgroup.api.model.Trade;

@DataJpaTest
class TradeRepositoryTests {

	@Autowired
	private TradeRepository tradeRepo;
	
	@Autowired
	private CompanyRepository companyRepo;
	
	Trade trade1, trade2;
	
	@BeforeEach
	void setUp() throws Exception {
		trade1 = new Trade();
		trade2 = new Trade();
	}

	@Test
	void test_getTrade_returnsValidId_whenValidTradeIsSaved() {
		tradeRepo.save(trade1);
		assertTrue(trade1.getId() > 0);
	}
	
	@Test
	void test_getDates_returnsCorrectListOfDates() {
		trade1.setTime(LocalDateTime.now());
		trade2.setTime(LocalDateTime.now().plusDays(1));
		tradeRepo.save(trade1);
		tradeRepo.save(trade2);
		
		List<LocalDate> dates = tradeRepo.getDates();
		assertEquals(dates.size(), 2);
		assertEquals(dates.get(0), LocalDate.now());
		assertEquals(dates.get(1), LocalDate.now().plusDays(1));
	}
	
	@Test
	void test_getDailyTrades_returnsCorrectListOfTrades() {
		trade1.setTime(LocalDateTime.now());
		trade2.setTime(LocalDateTime.now().plusDays(1));
		
		assertTrue(trade2.getTime().isAfter(LocalDateTime.now()));

		tradeRepo.save(trade1);
		tradeRepo.save(trade2);
		
		List<Trade> trades = tradeRepo.getDailyTrades(LocalDate.now());
		assertEquals(1, trades.size());
		assertTrue(trades.get(0).getTime().isBefore(LocalDateTime.now()));
	}
	
	@Test
	void test_getTradesByDateAndTicker_returnsCorrectListOfTrades() {
		trade1.setTime(LocalDateTime.now());
		trade2.setTime(LocalDateTime.now());
		
		Company company1 = new Company("TEST");
		Company company2 = new Company("FAIL");
		
		companyRepo.save(company1);
		companyRepo.save(company2);
		
		assertTrue(company1.getId() > 0);
		
		trade1.setCompany(company1);
		trade2.setCompany(company2);

		tradeRepo.save(trade1);
		tradeRepo.save(trade2);
		
		List<Trade> trades = tradeRepo.getTradesByDateAndTicker(LocalDate.now(), "TEST");

		assertEquals(1, trades.size());
		
		assertEquals("TEST", trades.get(0).getCompany().getCompanyTicker());
		assertFalse(trades.get(0).getCompany().getCompanyTicker().equals("FAIL"));
		
	}

}
