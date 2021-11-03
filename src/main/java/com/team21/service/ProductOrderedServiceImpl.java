package com.team21.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team21.dto.ProductOrderedDTO;
import com.team21.entity.ProductOrderEntity;
import com.team21.exception.OrderMSException;
import com.team21.repository.ProductOrderedRepository;
import com.team21.utility.CompositeKey;

@Transactional
@Service(value = "productOrderedService")
public class ProductOrderedServiceImpl implements ProductOrderedService {

	@Autowired
	ProductOrderedRepository productOrderedRepository;

	@Override
	public List<ProductOrderedDTO> viewOrderBySellerIdAndProductId(String sellerId, String prodId)
			throws OrderMSException {
		Iterable<ProductOrderEntity> productOrderEntities = productOrderedRepository.findAll();
		List<ProductOrderedDTO> productOrderedDTOs = new ArrayList<>();
		productOrderEntities.forEach(productOrderEntity -> {
			CompositeKey key = productOrderEntity.getCompositeID();

			if (productOrderEntity.getSellerId().equals(sellerId) && key.getProductId().equals(prodId)) {
				ProductOrderedDTO productOrderedDTO = new ProductOrderedDTO();
				productOrderedDTO.setBuyerId(key.getBuyerId());
				productOrderedDTO.setProductId(key.getProductId());
				productOrderedDTO.setQuantity(productOrderEntity.getQuantity());
				productOrderedDTO.setSellerId(productOrderEntity.getSellerId());
				productOrderedDTOs.add(productOrderedDTO);

			}

		});
		if (productOrderedDTOs.isEmpty())
			throw new OrderMSException("No Order for your this Product");
		return productOrderedDTOs;
	}

}
