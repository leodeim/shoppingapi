package com.shopping.api.service;

import com.shopping.api.domain.CartRuleType;
import com.shopping.api.domain.dto.CartRuleResponseTO;
import com.shopping.api.domain.dto.CartRuleTO;
import com.shopping.api.entity.CartRuleEntity;
import com.shopping.api.repo.CartRulesRepository;
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
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class CartRulesServiceTest {
	private static String TEST_STRING = "test_string";
	private static Long RULE_ID = 123L;

	@InjectMocks
	private CartRulesService cartRulesService;

	@Mock
	private CartRulesRepository cartRulesRepository;

	@Mock
	private CartRuleEntity cartRuleEntity;

	@Test
	void getAllRulesSuccessfulTest() {
		List<CartRuleEntity> list = Arrays.asList(cartRuleEntity);
		Mockito.when(cartRulesRepository.findAll()).thenReturn(list);

		var result = cartRulesService.getAllRules();

		Assertions.assertEquals(list.stream().map(CartRuleResponseTO::new).collect(Collectors.toList()), result);
		Mockito.verify(cartRulesRepository).findAll();
	}

	@Test
	void getAllRulesShouldThrowTest() {
		Mockito.when(cartRulesRepository.findAll()).thenThrow(new IllegalArgumentException(TEST_STRING));
		IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> cartRulesService.getAllRules());

		Assertions.assertEquals(TEST_STRING, e.getMessage());
	}

	@Test
	void addCartRuleSuccessfulTest() {
		var rule = new CartRuleTO(CartRuleType.ORDER_LIMIT, new BigDecimal(100), null, null, null);
		Mockito.when(cartRulesRepository.save(new CartRuleEntity(rule))).thenReturn(cartRuleEntity);
		var result = cartRulesService.addCartRule(rule);

		Assertions.assertEquals(new CartRuleResponseTO(cartRuleEntity), result);
	}

	@Test
	void addCartRuleShouldThrowTest() {
		var rule = new CartRuleTO(CartRuleType.ORDER_LIMIT, new BigDecimal(100), null, null, null);
		Mockito.when(cartRulesRepository.save(new CartRuleEntity(rule))).thenThrow(new IllegalArgumentException(TEST_STRING));
		IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> cartRulesService.addCartRule(rule));

		Assertions.assertEquals(TEST_STRING, e.getMessage());
	}

	@Test
	void deleteRuleSuccessfulTest() {
		Mockito.when(cartRulesRepository.findById(RULE_ID)).thenReturn(java.util.Optional.of(cartRuleEntity));
		cartRulesService.deleteCartRule(RULE_ID);

		Mockito.verify(cartRulesRepository).delete(cartRuleEntity);
	}

	@Test
	void deleteRuleShouldThrowTest() {
		Mockito.when(cartRulesRepository.findById(RULE_ID)).thenReturn(java.util.Optional.empty());
		ResponseStatusException e = Assertions.assertThrows(ResponseStatusException.class, () -> cartRulesService.deleteCartRule(RULE_ID));

		Assertions.assertEquals("404 NOT_FOUND \"Can't find this cart rule\"", e.getMessage());
	}

}
