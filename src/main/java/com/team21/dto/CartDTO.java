package com.team21.dto;

import java.util.Objects;

public class CartDTO {

	private String buyerId;
	private String prodId;
	private Integer quantity;

	// Getters and Setters
	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	// Utility Methods
	@Override
	public String toString() {
		return "CartDTO [buyerId=" + buyerId + ", prodId=" + prodId + ", quantity=" + quantity + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(buyerId, prodId, quantity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartDTO other = (CartDTO) obj;
		return Objects.equals(buyerId, other.buyerId) && Objects.equals(prodId, other.prodId)
				&& Objects.equals(quantity, other.quantity);
	}

}
