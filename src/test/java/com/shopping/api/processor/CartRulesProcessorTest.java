package com.shopping.api.processor;

import com.shopping.api.domain.dto.CartSummaryTO;
import com.shopping.api.domain.CartRuleType;
import com.shopping.api.domain.ProductCategory;
import com.shopping.api.domain.dto.CartRuleResponseTO;
import com.shopping.api.domain.dto.ProductTO;
import com.shopping.api.entity.CartItemEntity;
import com.shopping.api.entity.ProductEntity;
import com.shopping.api.service.CartRulesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CartRulesProcessorTest {

	@InjectMocks
	private CartRulesProcessor cartRulesProcessor;

	@Mock
	private CartRulesService cartRulesService;

	@Test
	void processSuccessfulTest() {
		Mockito.when(cartRulesService.getAllRules()).thenReturn(getCartRulesList());
		Assertions.assertEquals(getExpectedCartProperties(), cartRulesProcessor.process(getCartItemList()));
	}

	@Test
	void processSuccessfulTestWithoutDiscount() {
		Mockito.when(cartRulesService.getAllRules()).thenReturn(getCartRulesList());
		Assertions.assertEquals(getExpectedCartPropertiesWithoutDiscount(), cartRulesProcessor.process(getCartItemListWithoutDiscount()));
	}

	@Test
	void processSuccessfulWithLimitReachedTest() {
		Mockito.when(cartRulesService.getAllRules()).thenReturn(getCartRulesList());
		Assertions.assertEquals(getExpectedCartPropertiesWithLimitReached(), cartRulesProcessor.process(getCartItemListWithLimitReached()));
	}

	@Test
	void processSuccessfulInvalidDataOrderLimitTest() {
		Mockito.when(cartRulesService.getAllRules()).thenReturn(getCartRulesListInvalidDataOrderLimit());
		ResponseStatusException e = Assertions.assertThrows(ResponseStatusException.class,
				() -> cartRulesProcessor.process(getCartItemList()));

		Assertions.assertEquals("500 INTERNAL_SERVER_ERROR \"Wrong card rule data\"", e.getMessage());
	}

	@Test
	void processSuccessfulInvalidDataDiscountProductsTest1() {
		Mockito.when(cartRulesService.getAllRules()).thenReturn(getCartRulesListInvalidDataDiscountProducts1());
		ResponseStatusException e = Assertions.assertThrows(ResponseStatusException.class,
				() -> cartRulesProcessor.process(getCartItemList()));

		Assertions.assertEquals("500 INTERNAL_SERVER_ERROR \"Wrong card rule data\"", e.getMessage());
	}

	@Test
	void processSuccessfulInvalidDataDiscountProductsTest2() {
		Mockito.when(cartRulesService.getAllRules()).thenReturn(getCartRulesListInvalidDataDiscountProducts2());
		ResponseStatusException e = Assertions.assertThrows(ResponseStatusException.class,
				() -> cartRulesProcessor.process(getCartItemList()));

		Assertions.assertEquals("500 INTERNAL_SERVER_ERROR \"Wrong card rule data\"", e.getMessage());
	}

	@Test
	void processSuccessfulInvalidDataDiscountSumTest1() {
		Mockito.when(cartRulesService.getAllRules()).thenReturn(getCartRulesListInvalidDataDiscountSum1());
		ResponseStatusException e = Assertions.assertThrows(ResponseStatusException.class,
				() -> cartRulesProcessor.process(getCartItemList()));

		Assertions.assertEquals("500 INTERNAL_SERVER_ERROR \"Wrong card rule data\"", e.getMessage());
	}

	@Test
	void processSuccessfulInvalidDataDiscountSumTest2() {
		Mockito.when(cartRulesService.getAllRules()).thenReturn(getCartRulesListInvalidDataDiscountSum2());
		ResponseStatusException e = Assertions.assertThrows(ResponseStatusException.class,
				() -> cartRulesProcessor.process(getCartItemList()));

		Assertions.assertEquals("500 INTERNAL_SERVER_ERROR \"Wrong card rule data\"", e.getMessage());
	}

	private List<CartItemEntity> getCartItemList() {
		return Arrays.asList(
				new CartItemEntity(new ProductEntity(new ProductTO("test1", ProductCategory.BEVERAGES, BigDecimal.valueOf(10L))), 1),
				new CartItemEntity(new ProductEntity(new ProductTO("test2", ProductCategory.BEVERAGES, BigDecimal.valueOf(5L))), 5),
				new CartItemEntity(new ProductEntity(new ProductTO("test3", ProductCategory.FOOD, BigDecimal.valueOf(10L))), 3)
		);
	}

	private List<CartItemEntity> getCartItemListWithoutDiscount() {
		return Arrays.asList(
				new CartItemEntity(new ProductEntity(new ProductTO("test1", ProductCategory.BEVERAGES, BigDecimal.valueOf(3L))), 1),
				new CartItemEntity(new ProductEntity(new ProductTO("test2", ProductCategory.BEVERAGES, BigDecimal.valueOf(5L))), 1),
				new CartItemEntity(new ProductEntity(new ProductTO("test3", ProductCategory.FOOD, BigDecimal.valueOf(1L))), 1)
		);
	}

	private List<CartItemEntity> getCartItemListWithLimitReached() {
		return Arrays.asList(
				new CartItemEntity(new ProductEntity(new ProductTO("test1", ProductCategory.BEVERAGES, BigDecimal.valueOf(10L))), 1),
				new CartItemEntity(new ProductEntity(new ProductTO("test2", ProductCategory.BEVERAGES, BigDecimal.valueOf(5L))), 5),
				new CartItemEntity(new ProductEntity(new ProductTO("test3", ProductCategory.FOOD, BigDecimal.valueOf(10L))), 10)
		);
	}

	private List<CartRuleResponseTO> getCartRulesList() {
		return Arrays.asList(
				new CartRuleResponseTO(0L, CartRuleType.ORDER_LIMIT, new BigDecimal(100), null, null, null),
				new CartRuleResponseTO(1L, CartRuleType.DISCOUNT_PRODUCTS, null, null, null, 5),
				new CartRuleResponseTO(2L, CartRuleType.DISCOUNT_SUM, null, new BigDecimal(20), new BigDecimal(1), null)
		);
	}

	private List<CartRuleResponseTO> getCartRulesListInvalidDataOrderLimit() {
		return Arrays.asList(
				new CartRuleResponseTO(0L, CartRuleType.ORDER_LIMIT, null, null, null, null)
		);
	}

	private List<CartRuleResponseTO> getCartRulesListInvalidDataDiscountProducts1() {
		return Arrays.asList(
				new CartRuleResponseTO(0L, CartRuleType.DISCOUNT_PRODUCTS, null, null, null, null)
		);
	}

	private List<CartRuleResponseTO> getCartRulesListInvalidDataDiscountProducts2() {
		return Arrays.asList(
				new CartRuleResponseTO(0L, CartRuleType.DISCOUNT_PRODUCTS, null, null, null, 0)
		);
	}

	private List<CartRuleResponseTO> getCartRulesListInvalidDataDiscountSum1() {
		return Arrays.asList(
				new CartRuleResponseTO(0L, CartRuleType.DISCOUNT_SUM, null, BigDecimal.valueOf(1L), null, null)
		);
	}

	private List<CartRuleResponseTO> getCartRulesListInvalidDataDiscountSum2() {
		return Arrays.asList(
				new CartRuleResponseTO(0L, CartRuleType.DISCOUNT_SUM, null, null, BigDecimal.valueOf(1L), null)
		);
	}

	private CartSummaryTO getExpectedCartProperties() {
		var cartProperties = new CartSummaryTO();
		cartProperties.setLimitReached(false);
		cartProperties.setDiscount(BigDecimal.valueOf(6L));
		cartProperties.setSum(BigDecimal.valueOf(65L));
		return cartProperties;
	}

	private CartSummaryTO getExpectedCartPropertiesWithLimitReached() {
		var cartProperties = new CartSummaryTO();
		cartProperties.setLimitReached(true);
		cartProperties.setDiscount(BigDecimal.valueOf(26L));
		cartProperties.setSum(BigDecimal.valueOf(135L));
		return cartProperties;
	}

	private CartSummaryTO getExpectedCartPropertiesWithoutDiscount() {
		var cartProperties = new CartSummaryTO();
		cartProperties.setLimitReached(false);
		cartProperties.setDiscount(BigDecimal.valueOf(0L));
		cartProperties.setSum(BigDecimal.valueOf(9L));
		return cartProperties;
	}
}
