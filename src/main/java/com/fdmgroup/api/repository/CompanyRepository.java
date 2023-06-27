package com.fdmgroup.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fdmgroup.api.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

	@Query("select c from Company c where upper(c.companyTicker) like upper(:companyTicker)")
	Optional<Company> findByTicker(String companyTicker);

}
