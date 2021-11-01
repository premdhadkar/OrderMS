package com.team21.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team21.dto.CartDTO;
import com.team21.dto.OrderDTO;
import com.team21.dto.OrderPlacedDTO;
import com.team21.dto.ProductDTO;
import com.team21.service.OrderService;

@RestController
public class OrderController {

	@Value("${user.uri}")
	String userUri;

	@Value("${product.uri}")
	String productUri;

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

	// Place Order by Buyer
	@PostMapping(value = "/order/placeOrder/{buyerId}")
	public ResponseEntity<String> placeOrder(@PathVariable String buyerId, @RequestBody OrderDTO order) {

		try {

			ObjectMapper mapper = new ObjectMapper();
			List<ProductDTO> productList = new ArrayList<>();
			List<CartDTO> cartList = mapper.convertValue(
					new RestTemplate().getForObject(userUri + "/userMS/buyer/cart/get/" + buyerId, List.class),
					new TypeReference<List<CartDTO>>() {
					});

			cartList.forEach(item -> {
				ProductDTO prod = new RestTemplate().getForObject(productUri + "product/get/Id/" + item.getProdId(),
						ProductDTO.class);

				productList.add(prod);
				// msg later
			});

			OrderPlacedDTO orderPlaced = orderService.placeOrder(productList, cartList, order);
			cartList.forEach(item -> {
				new RestTemplate().getForObject(
						productUri + "product/reduce/stock/" + item.getProdId() + "/" + item.getQuantity(),
						Boolean.class);
				new RestTemplate().postForObject(
						userUri + "userMS/buyer/cart/remove/" + buyerId + "/" + item.getProdId(), null, String.class);
			});

			new RestTemplate().getForObject(
					userUri + "updateRewardPoints/" + buyerId + "/" + orderPlaced.getRewardPoints(), String.class);

			return new ResponseEntity<>(orderPlaced.getOrderId(), HttpStatus.ACCEPTED);
		} catch (Exception e) {
			String newMsg = "There was some error";
			if (e.getMessage().equals("404 null")) {
				newMsg = "Error while placing the order";
			}
			return new ResponseEntity<>(newMsg, HttpStatus.UNAUTHORIZED);
		}

	}

	// View Order History of Buyer using buyer Id
	@GetMapping(value = "/order/view/byBuyerId/{buyerId}")
	public ResponseEntity<List<OrderDTO>> viewsOrdersByBuyerId(@PathVariable String buyerId) {
		try {
			List<OrderDTO> allOrders = orderService.viewOrdersByBuyer(buyerId);
			return new ResponseEntity<>(allOrders, HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}

	// View Order using Order Id
	@GetMapping(value = "/order/view/byOrderId/{orderId}")
	public ResponseEntity<OrderDTO> viewOrderByOrderId(@PathVariable String orderId) {
		try {
			OrderDTO allOrders = orderService.viewOrderbyOrderId(orderId);
			return new ResponseEntity<>(allOrders, HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}

	@PostMapping(value = "/order/reOrder/{orderId}/{buyerId}")
	public ResponseEntity<String> reOrder(@PathVariable String buyerId, @PathVariable String orderId) {

		try {

			String reOrderId = orderService.reOrder(buyerId, orderId);
			return new ResponseEntity<>("Order ID: " + reOrderId, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
