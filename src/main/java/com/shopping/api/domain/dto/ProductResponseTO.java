package com.shopping.api.domain.dto;

import com.shopping.api.domain.ProductCategory;
import com.shopping.api.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseTO {
	private Long id;
	private String name;
	private ProductCategory category;
	private BigDecimal price;

	public ProductResponseTO(ProductEntity entity){
		this.id = entity.getId();
		this.name = entity.getName();
		this.category = entity.getCategory();
		this.price = entity.getPrice();
	}
}
