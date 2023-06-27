package com.fdmgroup.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "companygen")
	@SequenceGenerator(name = "companygen", sequenceName = "company_id_seq", allocationSize = 1)
	@Column(name = "company_id")
	private long id;
	@NotBlank(message = "Company ticker must not be null or blank.")
	@Size(min = 2, max = 5, message = "Company ticker must be longer than 2 characters and less than 250.")
	private String companyTicker = "blank";
	
	private double indexWeight = 0.;

	public Company() {
	}

	public Company(
			@Size(min = 2, max = 5, message = "Company ticker must be longer than 2 characters and less than 250.") String companyTicker) {
		super();
		this.companyTicker = companyTicker;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCompanyTicker() {
		return companyTicker;
	}

	public void setCompanyTicker(String companyTicker) {
		this.companyTicker = companyTicker;
	}
	public double getIndexWeight() {
		return indexWeight;
	}

	public void setIndexWeight(double indexWeight) {
		this.indexWeight = indexWeight;
	}
}
