package com.shopping.api.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartSummaryTO {
	private boolean limitReached;
	private BigDecimal sum;
	private BigDecimal discount;

	public CartSummaryTO() {
		this.limitReached = false;
		this.sum = new BigDecimal(0);
		this.discount = new BigDecimal(0);
	}
}
