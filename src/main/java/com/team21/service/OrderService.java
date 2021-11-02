package com.team21.service;

import java.util.List;

import com.team21.dto.OrderDTO;
import com.team21.dto.ProductDTO;
import com.team21.dto.ProductOrderedDTO;
import com.team21.exception.OrderMSException;
import com.team21.utility.CurrentOrderStatus;

public interface OrderService {

	public List<OrderDTO> viewAllOrders() throws OrderMSException;

	public List<OrderDTO> viewOrdersByBuyer(String buyerId) throws OrderMSException;

	public OrderDTO viewOrderbyOrderId(String orderId) throws OrderMSException;

	public String reOrder(String buyerId, String orderId) throws OrderMSException;

	public ProductOrderedDTO createProductOrderDTO(ProductDTO product, String buyerId, Integer quantity);

	public Float placeOrder(OrderDTO orderDTO, List<ProductOrderedDTO> productOrderedDTOs, List<ProductDTO> products,
			Integer rewardPoints) throws OrderMSException;

	public void updateOrderStatus(String orderId, CurrentOrderStatus status) throws OrderMSException;

}
