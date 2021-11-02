package com.team21.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.team21.utility.CurrentOrderStatus;

@Entity
@Table(name = "order_table")
public class OrderEntity {

	@Id
	private String orderId;
	private String buyerId;
	private Float amount;
	private LocalDate date;
	private String address;
	@Enumerated(EnumType.STRING)
	private CurrentOrderStatus status;

	// Getters and SettersS
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public CurrentOrderStatus getStatus() {
		return status;
	}

	public void setStatus(CurrentOrderStatus status) {
		this.status = status;
	}

}
