package com.team21.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.team21.entity.OrderEntity;

public interface OrderRepository extends CrudRepository<OrderEntity, String> {

	public List<OrderEntity> findByBuyerId(String buyerId);

	public Optional<OrderEntity> findByOrderId(String orderId);

}
