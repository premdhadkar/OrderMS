package com.team21.dto;

import java.time.LocalDate;
import java.util.Objects;

import com.team21.entity.OrderEntity;
import com.team21.utility.CurrentOrderStatus;

public class OrderDTO {

	private String orderId;
	private String buyerId;
	private Float amount;
	private LocalDate date;
	private String address;
	private CurrentOrderStatus status;

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

	@Override
	public int hashCode() {
		return Objects.hash(address, amount, buyerId, date, orderId, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderDTO other = (OrderDTO) obj;
		return Objects.equals(address, other.address) && Objects.equals(amount, other.amount)
				&& Objects.equals(buyerId, other.buyerId) && Objects.equals(date, other.date)
				&& Objects.equals(orderId, other.orderId) && status == other.status;
	}

	@Override
	public String toString() {
		return "OrderDTO [orderId=" + orderId + ", buyerId=" + buyerId + ", amount=" + amount + ", date=" + date
				+ ", address=" + address + ", status=" + status + "]";
	}

	public static OrderDTO createDTO(OrderEntity orderEntity) {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setAddress(orderEntity.getAddress());
		orderDTO.setAmount(orderEntity.getAmount());
		orderDTO.setBuyerId(orderEntity.getBuyerId());
		orderDTO.setDate(orderEntity.getDate());
		orderDTO.setOrderId(orderEntity.getOrderId());
		orderDTO.setStatus(orderEntity.getStatus());
		return orderDTO;
	}

}
