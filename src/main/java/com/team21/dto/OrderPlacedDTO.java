package com.team21.dto;

import java.util.Objects;

public class OrderPlacedDTO {

	private String orderId;
	private String buyerId;
	private Integer rewardPoints;

	// Getters and Setters
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

	public Integer getRewardPoints() {
		return rewardPoints;
	}

	public void setRewardPoints(Integer rewardPoints) {
		this.rewardPoints = rewardPoints;
	}

	// Utility Methods
	@Override
	public String toString() {
		return "OrderPlacedDTO [orderId=" + orderId + ", buyerId=" + buyerId + ", rewardPoints=" + rewardPoints + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(buyerId, orderId, rewardPoints);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderPlacedDTO other = (OrderPlacedDTO) obj;
		return Objects.equals(buyerId, other.buyerId) && Objects.equals(orderId, other.orderId)
				&& Objects.equals(rewardPoints, other.rewardPoints);
	}

}
