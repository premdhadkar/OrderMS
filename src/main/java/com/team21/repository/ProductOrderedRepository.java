package com.team21.repository;



import org.springframework.data.repository.CrudRepository;

import com.team21.entity.ProductOrderEntity;
import com.team21.utility.CompositeKey;

public interface ProductOrderedRepository extends CrudRepository<ProductOrderEntity, CompositeKey> {

}
