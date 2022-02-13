package com.shopping.api.entity;

import com.shopping.api.domain.ProductCategory;
import com.shopping.api.domain.dto.ProductTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@Table(name = "products")
public class ProductEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private ProductCategory category;

	@Column(nullable = false)
	private BigDecimal price;

	public ProductEntity(ProductTO productTO) {
		this.name = productTO.getName();
		this.category = productTO.getCategory();
		this.price = productTO.getPrice();
	}
}
