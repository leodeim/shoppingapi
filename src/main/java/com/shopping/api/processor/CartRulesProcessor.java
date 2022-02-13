package com.shopping.api.processor;

import com.shopping.api.domain.dto.CartSummaryTO;
import com.shopping.api.entity.CartItemEntity;
import com.shopping.api.service.CartRulesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartRulesProcessor {
	private final CartRulesService cartRulesService;

	public CartSummaryTO process(List<CartItemEntity> cartItems) {
		var rules = cartRulesService.getAllRules();

		var calculations = rules.stream().map(rule -> {
			var calculator = new CartRulesCalculatorFactory().getCalculator(rule.getType());
			return calculator.calculate(rule, cartItems);
		}).collect(Collectors.toCollection(ArrayList::new));

		return getCartSummary(calculations, cartItems);
	}

	private CartSummaryTO getCartSummary(List<CartSummaryTO> calculations, List<CartItemEntity> cartItems) {
		var cartSummary = new CartSummaryTO();

		cartSummary.setSum(cartItems.stream().map(CartItemEntity::getFullPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
		cartSummary.setDiscount(calculations.stream().map(CartSummaryTO::getDiscount).reduce(BigDecimal.ZERO, BigDecimal::add));
		cartSummary.setLimitReached(calculations.stream().map(CartSummaryTO::isLimitReached).reduce(Boolean.FALSE, Boolean::logicalOr));

		return cartSummary;
	}
}
