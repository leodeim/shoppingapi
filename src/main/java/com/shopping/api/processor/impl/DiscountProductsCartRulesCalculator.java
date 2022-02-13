package com.shopping.api.processor.impl;

import com.shopping.api.domain.dto.CartSummaryTO;
import com.shopping.api.domain.dto.CartRuleResponseTO;
import com.shopping.api.entity.CartItemEntity;
import com.shopping.api.processor.CartRulesCalculator;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

public class DiscountProductsCartRulesCalculator implements CartRulesCalculator {

	public CartSummaryTO calculate(CartRuleResponseTO rule, List<CartItemEntity> cartItems) {
		if (rule.getDiscountedProductNumber() ==  null || rule.getDiscountedProductNumber() == 0) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Wrong card rule data");
		}
		var calculation = new CartSummaryTO();

		cartItems.forEach(item -> {
			if (item.getQuantity() >= rule.getDiscountedProductNumber()) {
				var itemDiscount = item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity() / rule.getDiscountedProductNumber()));
				var newDiscount = calculation.getDiscount().add(itemDiscount);
				calculation.setDiscount(newDiscount);
			}
		});

		return calculation;
	}
}
