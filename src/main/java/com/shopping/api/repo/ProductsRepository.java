package com.shopping.api.repo;

import com.shopping.api.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository  extends JpaRepository<ProductEntity, Long> {
}