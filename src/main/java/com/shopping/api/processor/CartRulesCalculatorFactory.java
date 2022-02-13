package com.shopping.api.processor;

import com.shopping.api.domain.CartRuleType;
import com.shopping.api.processor.impl.DiscountProductsCartRulesCalculator;
import com.shopping.api.processor.impl.DiscountSumCartRulesCalculator;
import com.shopping.api.processor.impl.OrderLimitCartRulesCalculator;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CartRulesCalculatorFactory {
	public CartRulesCalculator getCalculator(CartRuleType cartRuleType) {
		switch (cartRuleType) {
			case DISCOUNT_PRODUCTS:
				return new DiscountProductsCartRulesCalculator();
			case DISCOUNT_SUM:
				return new DiscountSumCartRulesCalculator();
			case ORDER_LIMIT:
				return new OrderLimitCartRulesCalculator();
			default:
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Wrong card property type");
		}
	}
}
