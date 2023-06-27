package com.fdmgroup.api.model;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;

/**
 * <p>
 * Statistic resource that holds daily aggregate information about trades
 * for each company.
 * </p>
 * 
 * @author Jakob Buergermeister
 *
 */

@Entity
public class Statistic {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "statisticgen")
	@SequenceGenerator(name = "statisticgen", sequenceName = "statistic_id_seq", allocationSize = 1)
	private long id;
	
	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "company_id", referencedColumnName = "company_id")
	private Company company;
	private LocalDate date;
	private double openPrice;
	private double closePrice;
	private double highestPrice;
	private double lowestPrice;
	private double tradedVolume;

	public Statistic() {
	}

	public Statistic(Company company, LocalDate date, double openPrice, double closePrice, double highestPrice,
			double lowestPrice, double tradedVolume) {
		super();
		this.company = company;
		this.date = date;
		this.openPrice = openPrice;
		this.closePrice = closePrice;
		this.highestPrice = highestPrice;
		this.lowestPrice = lowestPrice;
		this.tradedVolume = tradedVolume;
	}
	
	

	@Override
	public String toString() {
		return "~~~~~~~~~~ " + company.getCompanyTicker() + " ~~~~~~~~~~\n Date: " + date.toString() + "\n Open price: " + String.format("%.3f", openPrice) + "\n Close price: "
				+ String.format("%.3f", closePrice) + "\n Highest price: " + String.format("%.3f", highestPrice) + "\n Lowest price: " + String.format("%.3f", lowestPrice) + "\n Traded volume: "
				+ String.format("%.3e", tradedVolume) + "\n--------------------------";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public double getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(double openPrice) {
		this.openPrice = openPrice;
	}

	public double getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(double closePrice) {
		this.closePrice = closePrice;
	}

	public double getHighestPrice() {
		return highestPrice;
	}

	public void setHighestPrice(double highestPrice) {
		this.highestPrice = highestPrice;
	}

	public double getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(double lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	public double getTradedVolume() {
		return tradedVolume;
	}

	public void setTradedVolume(double tradedVolume) {
		this.tradedVolume = tradedVolume;
	}
	
	

}
