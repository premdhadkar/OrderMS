package com.team21.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.team21.utility.CompositeKey;

@Entity
@Table(name = "products_ordered")
public class ProductOrderEntity {

	@EmbeddedId
	private CompositeKey compositeID;

	private String sellerId;
	private Integer quantity;

	// Getters and Setters
	public CompositeKey getCompositeID() {
		return compositeID;
	}

	public void setCompositeID(CompositeKey compositeID) {
		this.compositeID = compositeID;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
