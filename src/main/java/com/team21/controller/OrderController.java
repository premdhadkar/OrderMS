package com.team21.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.team21.dto.OrderDTO;
import com.team21.service.OrderService;

@RestController
public class OrderController {

	@Autowired
	OrderService orderService;

	// Get all Order List
	@GetMapping(value = "/order/getAll")
	public ResponseEntity<List<OrderDTO>> viewAllOrder() {
		try {
			List<OrderDTO> allOrders = orderService.viewAllOrders();
			return new ResponseEntity<>(allOrders, HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
}
