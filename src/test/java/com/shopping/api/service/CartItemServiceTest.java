package com.shopping.api.service;

import com.shopping.api.domain.dto.CartSummaryTO;
import com.shopping.api.domain.ProductCategory;
import com.shopping.api.domain.dto.CartItemResponseTO;
import com.shopping.api.domain.dto.CartItemTO;
import com.shopping.api.domain.dto.ProductTO;
import com.shopping.api.entity.*;
import com.shopping.api.processor.CartRulesProcessor;
import com.shopping.api.repo.CartItemsRepository;
import com.shopping.api.repo.ProductsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class CartItemServiceTest {
	private static String TEST_STRING = "test_string";
	private static Long TEST_ID = 123L;
	private static int TEST_QTY = 5;

	@InjectMocks
	private CartItemService cartItemService;

	@Mock
	private CartItemsRepository cartItemsRepository;

	@Mock
	private ProductsRepository productsRepository;

	@Mock
	private CartRulesProcessor cartRulesProcessor;

	@Mock
	private CartItemEntity cartItemEntity;

	@Mock
	private ProductEntity productEntity;

	@Mock
	private CartSummaryTO cartSummary;


	@Test
	void emptyCartSuccessfulTest() {
		cartItemService.emptyCart();
		Mockito.verify(cartItemsRepository).deleteAll();
	}

	@Test
	void getAllItemsSuccessfulTest() {
		List<CartItemEntity> list = Arrays.asList(new CartItemEntity(new ProductEntity(new ProductTO("test", ProductCategory.FOOD, BigDecimal.valueOf(1))), 2));
		Mockito.when(cartItemsRepository.findAll()).thenReturn(list);

		var result = cartItemService.getAllItems();

		Assertions.assertEquals(list.stream().map(CartItemResponseTO::new).collect(Collectors.toList()), result);
		Mockito.verify(cartItemsRepository).findAll();
	}

	@Test
	void getAllItemsShouldThrowTest() {
		Mockito.when(cartItemsRepository.findAll()).thenThrow(new IllegalArgumentException(TEST_STRING));
		IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> cartItemService.getAllItems());

		Assertions.assertEquals(TEST_STRING, e.getMessage());
	}

	@Test
	void addToCartSuccessfulTest() {
		var cartItemNew = new CartItemTO(TEST_ID, TEST_QTY);
		var newEntityItem = new CartItemEntity(productEntity, cartItemNew.getQuantity());
		List<CartItemEntity> list = new ArrayList<>();
		Mockito.when(productsRepository.findById(TEST_ID)).thenReturn(java.util.Optional.of(productEntity));
		Mockito.when(cartItemsRepository.findByProduct(productEntity)).thenReturn(Optional.empty());
		Mockito.when(cartItemsRepository.findAll()).thenReturn(list);
		Mockito.when(cartRulesProcessor.process(Mockito.any())).thenReturn(cartSummary);
		Mockito.when(cartSummary.isLimitReached()).thenReturn(false);

		var result = cartItemService.addToCart(cartItemNew);

		Mockito.verify(cartItemsRepository).save(newEntityItem);
		Assertions.assertEquals(cartSummary, result);
	}

	@Test
	void addToCartSuccessfulTestAlreadyInCart() {
		var cartItemNew = new CartItemTO(TEST_ID, TEST_QTY);
		List<CartItemEntity> list = new ArrayList<>();
		Mockito.when(productsRepository.findById(TEST_ID)).thenReturn(java.util.Optional.of(productEntity));
		Mockito.when(cartItemsRepository.findByProduct(productEntity)).thenReturn(java.util.Optional.of(cartItemEntity));
		Mockito.when(cartItemsRepository.findAll()).thenReturn(list);
		Mockito.when(cartRulesProcessor.process(Mockito.any())).thenReturn(cartSummary);
		Mockito.when(cartSummary.isLimitReached()).thenReturn(false);

		var result = cartItemService.addToCart(cartItemNew);

		Mockito.verify(cartItemEntity).setQuantity(TEST_QTY);
		Mockito.verify(cartItemsRepository).save(cartItemEntity);
		Assertions.assertEquals(cartSummary, result);
	}

	@Test
	void addToCartShouldThrowNotFoundProductTest() {
		var cartItemNew = new CartItemTO(TEST_ID, TEST_QTY);
		Mockito.when(productsRepository.findById(TEST_ID)).thenReturn(java.util.Optional.empty());

		ResponseStatusException e = Assertions.assertThrows(ResponseStatusException.class, () -> cartItemService.addToCart(cartItemNew));

		Assertions.assertEquals("404 NOT_FOUND \"Can't find this product\"", e.getMessage());
	}

	@Test
	void addToCartShouldThrowLimitReachedTest() {
		var cartItemNew = new CartItemTO(TEST_ID, TEST_QTY);
		List<CartItemEntity> list = new ArrayList<>();
		Mockito.when(productsRepository.findById(TEST_ID)).thenReturn(java.util.Optional.of(productEntity));
		Mockito.when(cartItemsRepository.findByProduct(productEntity)).thenReturn(Optional.empty());
		Mockito.when(cartItemsRepository.findAll()).thenReturn(list);
		Mockito.when(cartRulesProcessor.process(Mockito.any())).thenReturn(cartSummary);
		Mockito.when(cartSummary.isLimitReached()).thenReturn(true);

		ResponseStatusException e = Assertions.assertThrows(ResponseStatusException.class, () -> cartItemService.addToCart(cartItemNew));

		Assertions.assertEquals("406 NOT_ACCEPTABLE \"Order limit has been reached\"", e.getMessage());
	}

	@Test
	void deleteItemSuccessfulTest() {
		Mockito.when(cartItemsRepository.findById(TEST_ID)).thenReturn(java.util.Optional.of(cartItemEntity));
		cartItemService.deleteItem(TEST_ID);

		Mockito.verify(cartItemsRepository).delete(cartItemEntity);
	}

	@Test
	void deleteItemShouldThrowTest() {
		Mockito.when(cartItemsRepository.findById(TEST_ID)).thenReturn(java.util.Optional.empty());
		ResponseStatusException e = Assertions.assertThrows(ResponseStatusException.class, () -> cartItemService.deleteItem(TEST_ID));

		Assertions.assertEquals("404 NOT_FOUND \"Can't find this item\"", e.getMessage());
	}

	@Test
	void getCalculationsSuccessfulTest() {
		List<CartItemEntity> list = new ArrayList<>();
		Mockito.when(cartItemsRepository.findAll()).thenReturn(list);
		Mockito.when(cartRulesProcessor.process(list)).thenReturn(cartSummary);

		var result = cartItemService.getCalculations();

		Assertions.assertEquals(cartSummary, result);
	}
}
