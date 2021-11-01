package com.team21.service;

import java.util.List;

import com.team21.dto.OrderDTO;
import com.team21.exception.OrderMSException;

public interface OrderService {

	public List<OrderDTO> viewAllOrders() throws OrderMSException;

}
