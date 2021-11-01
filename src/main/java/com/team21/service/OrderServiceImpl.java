package com.team21.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team21.dto.OrderDTO;
import com.team21.entity.OrderEntity;
import com.team21.exception.OrderMSException;
import com.team21.repository.OrderRepository;

@Transactional
@Service(value = "orderService")
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRepository;

	// View All orders 
	@Override
	public List<OrderDTO> viewAllOrders() throws OrderMSException {
		Iterable<OrderEntity> allOrders = orderRepository.findAll();
		List<OrderDTO> orderList = new ArrayList<>();
		allOrders.forEach(order -> {
			OrderDTO orderDto = OrderDTO.createDTO(order);
			orderList.add(orderDto);
		});
		if (orderList.isEmpty())
			throw new OrderMSException("No orders available");
		return orderList;
	}

}
