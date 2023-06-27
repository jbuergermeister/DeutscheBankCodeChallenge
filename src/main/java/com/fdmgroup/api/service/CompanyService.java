package com.fdmgroup.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.api.model.Company;
import com.fdmgroup.api.repository.CompanyRepository;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepo;
	
	public Company addCompany(Company company) {
		Optional<Company> companyOpt = companyRepo.findByTicker(company.getCompanyTicker());
		if(companyOpt.isEmpty()) {
			return companyRepo.save(company);
		} else {
			return null;
		}
	}

	public List<Company> findAll() {
		return companyRepo.findAll();
		
	}
}
