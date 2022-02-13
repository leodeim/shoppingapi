package com.shopping.api.processor.impl;

import com.shopping.api.domain.dto.CartSummaryTO;
import com.shopping.api.domain.dto.CartRuleResponseTO;
import com.shopping.api.entity.CartItemEntity;
import com.shopping.api.processor.CartRulesCalculator;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

public class OrderLimitCartRulesCalculator implements CartRulesCalculator {

	public CartSummaryTO calculate(CartRuleResponseTO rule, List<CartItemEntity> cartItems) {
		if (rule.getOrderLimit() == null) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Wrong card rule data");
		}
		var calculation = new CartSummaryTO();
		var totalOrderSum = cartItems.stream().map(item -> item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		if (totalOrderSum.compareTo(rule.getOrderLimit()) > 0) {
			calculation.setLimitReached(true);
		}

		return calculation;
	}
}
