package com.team21.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team21.dto.BillDTO;
import com.team21.dto.OrderDTO;
import com.team21.dto.ProductDTO;
import com.team21.dto.ProductOrderedDTO;
import com.team21.entity.OrderEntity;
import com.team21.entity.ProductOrderEntity;
import com.team21.exception.OrderMSException;
import com.team21.repository.OrderRepository;
import com.team21.repository.ProductOrderedRepository;
import com.team21.utility.CompositeKey;
import com.team21.utility.CurrentOrderStatus;
import com.team21.validator.OrderValidator;

@Transactional
@Service(value = "orderService")
public class OrderServiceImpl implements OrderService {

	private static int orderCount;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	ProductOrderedRepository productOrderedRepository;

	static {
		orderCount = 100;
	}

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

	// Place order to checkout
	@Override
	public BillDTO placeOrder(OrderDTO orderDTO, List<ProductOrderedDTO> productOrderedDTOs, List<ProductDTO> products,
			Integer rewardPoints) throws OrderMSException {
		OrderValidator.validateOrder(orderDTO);

		Float amount = 0f;

		for (int i = 0; i < products.size(); i++) {
			OrderValidator.validateStock(productOrderedDTOs.get(i), products.get(i));
			amount += (products.get(i).getPrice() * productOrderedDTOs.get(i).getQuantity());
		}
		String id = "ORD" + orderCount++;
		Integer discount = (rewardPoints / 4);
		amount = amount - discount;
		orderDTO.setAmount(amount);
		OrderEntity order = new OrderEntity();
		order.setOrderId(id);
		order.setAddress(orderDTO.getAddress());
		order.setAmount(amount);
		order.setBuyerId(orderDTO.getBuyerId());
		order.setDate(LocalDate.now());
		order.setStatus(CurrentOrderStatus.ORDER_PLACED);

		orderRepository.save(order);

		for (ProductOrderedDTO productOrderedDTO : productOrderedDTOs) {
			CompositeKey key = new CompositeKey(productOrderedDTO.getBuyerId(), productOrderedDTO.getProductId());
			ProductOrderEntity productOrderedEntity = new ProductOrderEntity();
			productOrderedEntity.setQuantity(productOrderedDTO.getQuantity());
			productOrderedEntity.setSellerId(productOrderedDTO.getSellerId());
			productOrderedEntity.setCompositeID(key);

			productOrderedRepository.save(productOrderedEntity);
		}

		BillDTO bill = new BillDTO();
		bill.setAmount(amount);
		bill.setOrderId(order.getOrderId());
		return bill;
	}

	// View Order History of Buyer using buyer Id
	@Override
	public List<OrderDTO> viewOrdersByBuyer(String buyerId) throws OrderMSException {
		List<OrderEntity> orders = orderRepository.findByBuyerId(buyerId);
		if (orders.isEmpty())
			throw new OrderMSException("No order history found for given BuyerID");
		List<OrderDTO> orderDTOs = new ArrayList<>();
		orders.forEach(order -> {
			OrderDTO orderDTO = OrderDTO.createDTO(order);
			orderDTOs.add(orderDTO);
		});
		return orderDTOs;
	}

	// View Order using Order Id
	@Override
	public OrderDTO viewOrderbyOrderId(String orderId) throws OrderMSException {
		Optional<OrderEntity> optional = orderRepository.findByOrderId(orderId);
		OrderEntity order = optional.orElseThrow(() -> new OrderMSException("Order does not exist"));
		OrderDTO orderDTO = OrderDTO.createDTO(order);
		return orderDTO;
	}

	// reorder previously ordered
	@Override
	public String reOrder(String orderId) throws OrderMSException {
		Optional<OrderEntity> optional = orderRepository.findByOrderId(orderId);
		OrderEntity order = optional
				.orElseThrow(() -> new OrderMSException("No order history found for given BuyerID"));
		OrderEntity reOrderEntity = new OrderEntity();
		String reOrderId = "ORD" + orderCount++;

		reOrderEntity.setOrderId(reOrderId);
		reOrderEntity.setBuyerId(order.getBuyerId());
		reOrderEntity.setAmount(order.getAmount());
		reOrderEntity.setAddress(order.getAddress());
		reOrderEntity.setDate(LocalDate.now());
		reOrderEntity.setStatus(CurrentOrderStatus.ORDER_PLACED);

		orderRepository.save(reOrderEntity);
		return reOrderEntity.getOrderId();
	}

	// Create ProductOrderedDTO to use in place order controller
	@Override
	public ProductOrderedDTO createProductOrderDTO(ProductDTO product, String buyerId, Integer quantity) {
		ProductOrderedDTO productOrderedDTO = new ProductOrderedDTO();

		productOrderedDTO.setBuyerId(buyerId);
		productOrderedDTO.setSellerId(product.getSellerId());
		productOrderedDTO.setProductId(product.getProdId());
		productOrderedDTO.setQuantity(quantity);
		return productOrderedDTO;
	}

	// Order status
	@Override
	public void updateOrderStatus(String orderId, CurrentOrderStatus status) throws OrderMSException {
		Optional<OrderEntity> order = orderRepository.findById(orderId);
		if (order.isPresent() == true) {
			order.get().setStatus(status);
			orderRepository.save(order.get());
		} else
			throw new OrderMSException("Order not found!!");

	}

}
