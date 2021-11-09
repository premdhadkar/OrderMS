package com.team21.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.team21.dto.BillDTO;
import com.team21.dto.OrderDTO;

import com.team21.dto.ProductDTO;
import com.team21.dto.ProductOrderedDTO;
import com.team21.exception.OrderMSException;
import com.team21.service.OrderService;
import com.team21.service.ProductOrderedService;
import com.team21.utility.CurrentOrderStatus;

@RestController
public class OrderController {

	@Value("${user.uri}")
	String userUri;

	@Value("${product.uri}")
	String productUri;

	@Autowired
	OrderService orderService;

	@Autowired
	ProductOrderedService productOrderedService;

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
	@PostMapping(value = "/order/place", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> placeOrder(@RequestBody OrderDTO orderDTO) {

		try {
			Integer rewardPoints = new RestTemplate()
					.getForObject(userUri + "userMS/get/rewardPoints/" + orderDTO.getBuyerId(), Integer.class);

			@SuppressWarnings("unchecked")
			List<String> productIdList = new RestTemplate()
					.getForObject(userUri + "cart/product/" + orderDTO.getBuyerId(), List.class);

			List<ProductDTO> products = new ArrayList<>();
			List<ProductOrderedDTO> productOrderedDTOs = new ArrayList<>();

			for (String productId : productIdList) {
				ProductDTO product = new RestTemplate().getForObject(productUri + "product/get/Id/" + productId,
						ProductDTO.class);

				Integer quantity = new RestTemplate().getForObject(
						userUri + "/cart/product/quantity/" + orderDTO.getBuyerId() + "/" + productId, Integer.class);

				if (product != null) {
					productOrderedDTOs
							.add(orderService.createProductOrderDTO(product, orderDTO.getBuyerId(), quantity));
					products.add(product);
				}
			}

			BillDTO bill = orderService.placeOrder(orderDTO, productOrderedDTOs, products, rewardPoints);

			new RestTemplate().postForObject(userUri + "userMS/updateRewards/" + orderDTO.getBuyerId(),
					bill.getAmount(), Boolean.class);

			for (ProductOrderedDTO productOrderedDTO : productOrderedDTOs) {

				new RestTemplate().put(userUri + "userMS/buyer/cart/remove/" + orderDTO.getBuyerId() + "/"
						+ productOrderedDTO.getProductId(), bill.getAmount());

				new RestTemplate().getForObject(productUri + "product/reduce/stock/" + productOrderedDTO.getProductId()
						+ "/" + productOrderedDTO.getQuantity(), Boolean.class);

			}
			return new ResponseEntity<>("Order Placed Successfully! you order ID is : " + bill.getOrderId(),
					HttpStatus.OK);

		} catch (HttpClientErrorException e) {
			return new ResponseEntity<>("Some Error Occured", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	// View Order History of Buyer using buyer Id
	@GetMapping(value = "/order/view/byBuyerId/{buyerId}")
	public ResponseEntity<List<OrderDTO>> viewsOrdersByBuyerId(@PathVariable String buyerId)
			throws ResponseStatusException {
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

	// reorder previously ordered
	@PostMapping(value = "/order/reOrder/{orderId}")
	public ResponseEntity<String> reOrder(@PathVariable String orderId) throws Exception {

		try {

			String reOrderId = orderService.reOrder(orderId);
			return new ResponseEntity<>("Your Re-order is successful, Re-Order ID: " + reOrderId, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}

	// Update order status
	@PostMapping(value = "/order/status/update/{orderId}/{status}")
	public ResponseEntity<String> updateStatus(@PathVariable String orderId, @PathVariable CurrentOrderStatus status) {
		try {

			orderService.updateOrderStatus(orderId, status);
			return new ResponseEntity<String>("Status updated successfully!", HttpStatus.OK);
		} catch (OrderMSException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}

	// View orders on sellers product Products
	@GetMapping(value = "/order/view/bySellersProducts/{sellerId}/{prodId}")
	public ResponseEntity<List<ProductOrderedDTO>> viewOrderBySellerIdAndProductId(@PathVariable String sellerId,
			@PathVariable String prodId) {
		try {
			List<ProductOrderedDTO> productOrderedDTO = productOrderedService.viewOrderBySellerIdAndProductId(sellerId,
					prodId);

			return new ResponseEntity<List<ProductOrderedDTO>>(productOrderedDTO, HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
}
