package com.shopping.api.domain.dto;

import com.shopping.api.entity.CartItemEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponseTO {
	private Long id;
	private ProductResponseTO product;
	private int quantity;

	public BigDecimal getFullPrice() {
		return this.product.getPrice().multiply(new BigDecimal(this.quantity));
	}

	public CartItemResponseTO(CartItemEntity entity) {
		this.id = entity.getId();
		this.quantity = entity.getQuantity();
		this.product = new ProductResponseTO(entity.getProduct());
	}
}
