package com.team21.validator;

import com.team21.dto.CartDTO;
import com.team21.dto.OrderDTO;
import com.team21.dto.ProductDTO;
import com.team21.exception.OrderMSException;

public class OrderValidator {

	public static void validateOrder(OrderDTO order) throws OrderMSException {

		// Address should not exceed 100 characters
		if (!validateAddress(order.getAddress()))
			throw new OrderMSException("Invalid Address Length! Hint: Max. allowed address length is 100");

	}

	public static void validateStock(CartDTO cartItem, ProductDTO productDTO) throws OrderMSException {

		// Validate if Stock is sufficient for order
		if (!validateStock(productDTO.getStock(), cartItem.getQuantity()))
			throw new OrderMSException("Insufficient stock");
	}

	private static boolean validateAddress(String address) {
		return (address.length() > 0 && address.length() < 100);
	}

	private static boolean validateStock(Integer stock, Integer quantity) {
		return stock >= quantity;
	}
}
