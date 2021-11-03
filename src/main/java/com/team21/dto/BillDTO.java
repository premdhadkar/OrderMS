package com.team21.dto;

import java.util.Objects;

public class BillDTO {

	private Float amount;
	private String orderId;

	// Getters and Setters
	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	// Utility Methods
	@Override
	public String toString() {
		return "BillDTO [amount=" + amount + ", orderId=" + orderId + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, orderId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BillDTO other = (BillDTO) obj;
		return Objects.equals(amount, other.amount) && Objects.equals(orderId, other.orderId);
	}

}
