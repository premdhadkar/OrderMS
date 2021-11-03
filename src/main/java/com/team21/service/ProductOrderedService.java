package com.team21.service;

import java.util.List;

import com.team21.dto.ProductOrderedDTO;
import com.team21.exception.OrderMSException;

public interface ProductOrderedService {
	
	public List<ProductOrderedDTO> viewOrderBySellerIdAndProductId(String sellerId, String prodId) throws OrderMSException;

}
