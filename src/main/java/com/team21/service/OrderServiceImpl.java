package com.team21.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team21.dto.CartDTO;
import com.team21.dto.OrderDTO;
import com.team21.dto.OrderPlacedDTO;
import com.team21.dto.ProductDTO;
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

	@Override
	public OrderPlacedDTO placeOrder(List<ProductDTO> productList, List<CartDTO> cartList, OrderDTO orderDTO)
			throws OrderMSException {
		OrderEntity order = new OrderEntity();
		OrderValidator.validateOrder(orderDTO);
		String id = "ORD" + orderCount++;
		order.setOrderId(id);
		order.setAddress(orderDTO.getAddress());
		order.setBuyerId(cartList.get(0).getBuyerId());
		order.setDate(LocalDate.now());
		order.setStatus(CurrentOrderStatus.ORDER_PLACED.toString());
		order.setAmount(0f);
		List<ProductOrderEntity> productsOrdered = new ArrayList<>();
		for (int i = 0; i < cartList.size(); i++) {
			OrderValidator.validateStock(cartList.get(i), productList.get(i));
			order.setAmount(order.getAmount() + (cartList.get(i).getQuantity() * productList.get(i).getPrice()));
			// CartList size and productList mismatch
			ProductOrderEntity prodO = new ProductOrderEntity();
			prodO.setSellerId(productList.get(i).getSellerId());
			prodO.setCompositeID(new CompositeKey(cartList.get(i).getBuyerId(), productList.get(i).getProdId()));
			prodO.setQuantity(cartList.get(i).getQuantity());
			// order id required
			productsOrdered.add(prodO);
		}
		productOrderedRepository.saveAll(productsOrdered);
		orderRepository.save(order);
		OrderPlacedDTO orderPlaced = new OrderPlacedDTO();
		orderPlaced.setBuyerId(order.getBuyerId());
		orderPlaced.setOrderId(order.getOrderId());
		Integer rewardPts = (int) (order.getAmount() / 100);
		orderPlaced.setRewardPoints(rewardPts);

		return orderPlaced;
	}

	@Override
	public List<OrderDTO> viewOrdersByBuyer(String buyerId) throws OrderMSException {
		List<OrderEntity> orders = orderRepository.findByBuyerId(buyerId);
		if (orders.isEmpty())
			throw new OrderMSException("No orders available for given BuyerID");
		List<OrderDTO> orderDTOs = new ArrayList<>();
		orders.forEach(order -> {
			OrderDTO orderDTO = OrderDTO.createDTO(order);
			orderDTOs.add(orderDTO);
		});
		return orderDTOs;
	}

}
