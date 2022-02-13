package com.shopping.api.processor;

import com.shopping.api.domain.CartRuleType;
import com.shopping.api.processor.impl.DiscountProductsCartRulesCalculator;
import com.shopping.api.processor.impl.DiscountSumCartRulesCalculator;
import com.shopping.api.processor.impl.OrderLimitCartRulesCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class CartRulesCalculatorTest {
	private CartRulesCalculatorFactory cartRulesCalculatorFactory;

	@BeforeEach
	void init() {
		cartRulesCalculatorFactory = new CartRulesCalculatorFactory();
	}

	@Test
	void getCalculatorDiscountProductsCalculator() {
		var result = cartRulesCalculatorFactory.getCalculator(CartRuleType.DISCOUNT_PRODUCTS);
		Assertions.assertEquals(DiscountProductsCartRulesCalculator.class, result.getClass());
	}

	@Test
	void getCalculatorDiscountSumCalculator() {
		var result = cartRulesCalculatorFactory.getCalculator(CartRuleType.DISCOUNT_SUM);
		Assertions.assertEquals(DiscountSumCartRulesCalculator.class, result.getClass());
	}

	@Test
	void getCalculatorOrderLimitCalculator() {
		var result = cartRulesCalculatorFactory.getCalculator(CartRuleType.ORDER_LIMIT);
		Assertions.assertEquals(OrderLimitCartRulesCalculator.class, result.getClass());
	}

	@Test
	void getCalculatorShouldThrow() {
		ResponseStatusException e = Assertions.assertThrows(ResponseStatusException.class,
				() -> cartRulesCalculatorFactory.getCalculator(CartRuleType.OTHER));

		Assertions.assertEquals("500 INTERNAL_SERVER_ERROR \"Wrong card property type\"", e.getMessage());
	}
}
