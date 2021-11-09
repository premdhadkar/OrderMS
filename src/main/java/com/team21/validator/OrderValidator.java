package com.team21.validator;

import com.team21.dto.OrderDTO;
import com.team21.dto.ProductDTO;
import com.team21.dto.ProductOrderedDTO;
import com.team21.exception.OrderMSException;

public class OrderValidator {

	private OrderValidator() {
		super();
	}

	public static void validateOrder(OrderDTO order) throws OrderMSException {

		// Address should not exceed 100 characters
		if (!validateAddress(order.getAddress()))
			throw new OrderMSException("Invalid Address Length! Hint: Max. allowed address length is 100");

	}

	public static void validateStock(ProductOrderedDTO productOrderedDTOItem, ProductDTO productDTO)
			throws OrderMSException {

		// Validate if Stock is sufficient for order
		if (!validateStock(productDTO.getStock(), productOrderedDTOItem.getQuantity()))
			throw new OrderMSException("Insufficient stock");
	}

	private static boolean validateAddress(String address) {
		return (address.length() > 0 && address.length() < 100);
	}

	private static boolean validateStock(Integer stock, Integer quantity) {
		return stock >= quantity;
	}
}
