package com.fdmgroup.api.model;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.DecimalMin;

/**
 * <h1>Trade class</h1>
 * 
 * @author Jakob Buergermeister
 *
 */

@Entity
public class Trade {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tradegen")
	@SequenceGenerator(name = "tradegen", sequenceName = "trade_id_seq", allocationSize = 1)
	private long id;
//	@NotBlank(message = "Time of trade must not be null or blank.")
	private LocalDateTime time;
	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "company_id", referencedColumnName = "company_id")
	private Company company;
	@DecimalMin(value = "0.0", message = "Price must be greater than 0.")
	private double price;
//	@NotBlank(message = "Traded number must not be null or blank.")
	private long numberTraded;

	public Trade(LocalDateTime time, Company company, double price, long numberTraded) {
		super();
		this.time = time;
		this.company = company;
		this.price = price;
		this.numberTraded = numberTraded;
	}

	public Trade() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public long getNumberTraded() {
		return numberTraded;
	}

	public void setNumberTraded(long numberTraded) {
		this.numberTraded = numberTraded;
	}

}
