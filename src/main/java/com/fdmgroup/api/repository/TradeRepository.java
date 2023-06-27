package com.fdmgroup.api.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fdmgroup.api.model.Trade;

/**
 * <p>
 * Repository for Trade resources include JpaRepository functionality, a method find
 * trades filtered by date and company ticker, a method to find the distinct
 * dates of recorded trades as well as a method to get all trades of one day.
 * </p>
 * 
 * @author Jakob Buergermeister
 *
 */
public interface TradeRepository extends JpaRepository<Trade, Long> {

	@Query("select t"
			+ " from Trade t"
			+ " where upper(t.company.companyTicker) like upper(?2)"
			+ " and cast(t.time as LocalDate) between ?1 and DATEADD(day, 1, CAST(?1 AS DATE))"
			+ " order by t.time")
	List<Trade> getTradesByDateAndTicker(LocalDate date, String ticker);

	@Query("select distinct CAST(t.time AS LocalDate)"
			+ " from Trade t")
	List<LocalDate> getDates();

	@Query("select t"
			+ " from Trade t"
			+ " where cast(t.time as LocalDate) like ?1"
			+ " order by t.time")
	List<Trade> getDailyTrades(LocalDate day);
}
