package com.shopping.api.domain.dto;

import com.shopping.api.domain.CartRuleType;
import com.shopping.api.entity.CartRuleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartRuleResponseTO {
	private Long id;
	private CartRuleType type;
	private BigDecimal orderLimit;
	private BigDecimal orderSum;
	private BigDecimal discount;
	private Integer discountedProductNumber;

	public CartRuleResponseTO(CartRuleEntity entity) {
		this.id = entity.getId();
		this.type = entity.getType();
		this.orderLimit = entity.getOrderLimit();
		this.orderSum = entity.getOrderSum();
		this.discount = entity.getDiscount();
		this.discountedProductNumber = entity.getDiscountedProductNumber();
	}
}
