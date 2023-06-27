package com.fdmgroup.api.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fdmgroup.api.model.Statistic;

/**
 * <p>
 * Repository for Statistic resources include JpaRepository functionality, a method find
 * statistics filtered by date and company ticker and a method to determine the earliest
 * recorded date.
 * </p>
 * 
 * @author Jakob Buergermeister
 *
 */
public interface StatisticRepository extends JpaRepository<Statistic, Long> {

	@Query("select stat from Statistic stat"
			+ " where stat.company.companyTicker like :companyTicker"
			+ " and stat.date like CAST(:day AS DATE)")
	List<Statistic> findByDateAndTicker(LocalDate day, String companyTicker);

	@Query("select CAST(MIN(stat.date) AS LocalDate) from Statistic stat")
	LocalDate getEarliestDate();

}
