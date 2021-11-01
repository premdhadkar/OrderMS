package com.team21.service;

import java.util.List;

import com.team21.dto.CartDTO;
import com.team21.dto.OrderDTO;
import com.team21.dto.OrderPlacedDTO;
import com.team21.dto.ProductDTO;
import com.team21.exception.OrderMSException;

public interface OrderService {

	public List<OrderDTO> viewAllOrders() throws OrderMSException;
	
	public OrderPlacedDTO placeOrder(List<ProductDTO> productList, List<CartDTO> cartList, OrderDTO order) throws OrderMSException;

}