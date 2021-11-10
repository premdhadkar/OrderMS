package com.team21.utility;

import java.io.Serializable;
import java.util.Objects;

public class CompositeKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String buyerId;
	protected String productId;

	// Constructors
	public CompositeKey() {
		super();
	}

	public CompositeKey(String buyerId, String productId) {
		super();
		this.buyerId = buyerId;
		this.productId = productId;
	}

	// Getters and Setters
	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// Utility Methods
	@Override
	public int hashCode() {
		return Objects.hash(buyerId, productId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompositeKey other = (CompositeKey) obj;
		return Objects.equals(buyerId, other.buyerId) && Objects.equals(productId, other.productId);
	}

}
