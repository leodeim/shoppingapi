package com.shopping.api.config;

import com.shopping.api.domain.CartRuleType;
import com.shopping.api.domain.ProductCategory;
import com.shopping.api.domain.dto.CartRuleTO;
import com.shopping.api.domain.dto.ProductTO;
import com.shopping.api.service.CartRulesService;
import com.shopping.api.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
@RequiredArgsConstructor
public class DevData {
	private final ProductsService productsService;
	private final CartRulesService cartRulesService;

	@Value("${populate.dev.data:false}")
	private String populateDevData;

	@Bean
	public void populateDevData() {
		var products = productsService.getProducts();
		var allRules = cartRulesService.getAllRules();

		if (populateDevData.equals("true") && products.isEmpty() && allRules.isEmpty()) {
			productsService.addProduct(new ProductTO("Cola", ProductCategory.BEVERAGES, BigDecimal.valueOf(1.59)));
			productsService.addProduct(new ProductTO("Fanta", ProductCategory.BEVERAGES, BigDecimal.valueOf(2.11)));
			productsService.addProduct(new ProductTO("Pepsi", ProductCategory.BEVERAGES, BigDecimal.valueOf(3.46)));
			productsService.addProduct(new ProductTO("Snickers", ProductCategory.FOOD, BigDecimal.valueOf(10.58)));
			productsService.addProduct(new ProductTO("Twix", ProductCategory.FOOD, BigDecimal.valueOf(1.51)));
			productsService.addProduct(new ProductTO("Mars", ProductCategory.FOOD, BigDecimal.valueOf(7.02)));

			cartRulesService.addCartRule(new CartRuleTO(CartRuleType.ORDER_LIMIT, new BigDecimal(100), null, null, null));
			cartRulesService.addCartRule(new CartRuleTO(CartRuleType.DISCOUNT_PRODUCTS, null, null, null, 5));
			cartRulesService.addCartRule(new CartRuleTO(CartRuleType.DISCOUNT_SUM, null, new BigDecimal(20), new BigDecimal(1), null));
		}
	}
}
