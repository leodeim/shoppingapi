package com.shopping.api.repo;

import com.shopping.api.entity.CartItemEntity;
import com.shopping.api.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemsRepository extends JpaRepository<CartItemEntity, Long> {
	Optional<CartItemEntity> findByProduct(ProductEntity product);
}
