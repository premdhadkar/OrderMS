package com.team21.dto;

import java.util.Objects;

public class ProductDTO {

	private String prodId;
	private String productName;
	private Float price;
	private Integer stock;
	private String description;
	private String image;
	private String category;
	private String sellerId;
	private String subCategory;
	private Float productRating;

	// Getters and Setters
	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public Float getProductRating() {
		return productRating;
	}

	public void setProductRating(Float productRating) {
		this.productRating = productRating;
	}

	// Utility Methods
	@Override
	public String toString() {
		return "ProductDTO [prodId=" + prodId + ", productName=" + productName + ", price=" + price + ", stock=" + stock
				+ ", description=" + description + ", image=" + image + ", category=" + category + ", sellerId="
				+ sellerId + ", subCategory=" + subCategory + ", productRating=" + productRating + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(category, description, image, price, prodId, productName, productRating, sellerId, stock,
				subCategory);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductDTO other = (ProductDTO) obj;
		return Objects.equals(category, other.category) && Objects.equals(description, other.description)
				&& Objects.equals(image, other.image) && Objects.equals(price, other.price)
				&& Objects.equals(prodId, other.prodId) && Objects.equals(productName, other.productName)
				&& Objects.equals(productRating, other.productRating) && Objects.equals(sellerId, other.sellerId)
				&& Objects.equals(stock, other.stock) && Objects.equals(subCategory, other.subCategory);
	}

}
