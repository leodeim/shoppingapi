package com.shopping.api.processor;

import com.shopping.api.domain.dto.CartSummaryTO;
import com.shopping.api.domain.dto.CartRuleResponseTO;
import com.shopping.api.entity.CartItemEntity;

import java.util.List;

public interface CartRulesCalculator {
	CartSummaryTO calculate(CartRuleResponseTO rule, List<CartItemEntity> cartItems);
}
