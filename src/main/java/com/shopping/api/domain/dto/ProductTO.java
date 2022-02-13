package com.shopping.api.domain.dto;

import com.shopping.api.domain.ProductCategory;
import com.shopping.api.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductTO {
	@NotNull
	private String name;
	@NotNull
	private ProductCategory category;
	@NotNull
	private BigDecimal price;

	public ProductTO(ProductEntity entity) {
		this.name = entity.getName();
		this.category = entity.getCategory();
		this.price = entity.getPrice();
	}
}
