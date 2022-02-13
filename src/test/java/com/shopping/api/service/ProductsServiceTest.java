package com.shopping.api.service;

import com.shopping.api.domain.ProductCategory;
import com.shopping.api.domain.dto.CartItemResponseTO;
import com.shopping.api.domain.dto.ProductResponseTO;
import com.shopping.api.domain.dto.ProductTO;
import com.shopping.api.entity.ProductEntity;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class ProductsServiceTest {
	private static String TEST_STRING = "test_string";
	private static Long PRODUCT_ID = 123L;

	@InjectMocks
	private ProductsService productsService;

	@Mock
	private ProductsRepository productsRepository;

	@Mock
	private ProductEntity productEntity;

	@Test
	void addProductSuccessfulTest() {
		var product = new ProductTO(TEST_STRING, ProductCategory.BEVERAGES, BigDecimal.valueOf(999));
		Mockito.when(productsRepository.save(new ProductEntity(product))).thenReturn(productEntity);
		var result = productsService.addProduct(product);

		Assertions.assertEquals(productEntity, result);
	}

	@Test
	void addProductShouldThrowTest() {
		var product = new ProductTO(TEST_STRING, ProductCategory.BEVERAGES, BigDecimal.valueOf(999));
		Mockito.when(productsRepository.save(new ProductEntity(product))).thenThrow(new IllegalArgumentException(TEST_STRING));
		IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> productsService.addProduct(product));

		Assertions.assertEquals(TEST_STRING, e.getMessage());
	}

	@Test
	void deleteProductSuccessfulTest() {
		Mockito.when(productsRepository.findById(PRODUCT_ID)).thenReturn(java.util.Optional.of(productEntity));
		productsService.deleteProduct(PRODUCT_ID);

		Mockito.verify(productsRepository).delete(productEntity);
	}

	@Test
	void deleteProductShouldThrowTest() {
		Mockito.when(productsRepository.findById(PRODUCT_ID)).thenReturn(java.util.Optional.empty());
		ResponseStatusException e = Assertions.assertThrows(ResponseStatusException.class, () -> productsService.deleteProduct(PRODUCT_ID));

		Assertions.assertEquals("404 NOT_FOUND \"Can't find this product\"", e.getMessage());
	}

	@Test
	void getProductsSuccessfulTest() {
		List<ProductEntity> list = Arrays.asList(new ProductEntity(new ProductTO("test", ProductCategory.FOOD, BigDecimal.valueOf(1))));
		Mockito.when(productsRepository.findAll()).thenReturn(list);

		var result = productsService.getProducts();

		Assertions.assertEquals(list.stream().map(ProductResponseTO::new).collect(Collectors.toList()), result);
		Mockito.verify(productsRepository).findAll();
	}

	@Test
	void getProductsShouldThrowTest() {
		Mockito.when(productsRepository.findAll()).thenThrow(new IllegalArgumentException(TEST_STRING));
		IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> productsService.getProducts());

		Assertions.assertEquals(TEST_STRING, e.getMessage());
	}

	@Test
	void getProductSuccessfulTest() {
		Mockito.when(productsRepository.findById(PRODUCT_ID)).thenReturn(java.util.Optional.of(productEntity));
		var result = productsService.getProduct(PRODUCT_ID);

		Mockito.verify(productsRepository).findById(PRODUCT_ID);
		Assertions.assertEquals(new ProductResponseTO(productEntity), result);
	}

	@Test
	void getProductShouldThrowTest() {
		Mockito.when(productsRepository.findById(PRODUCT_ID)).thenReturn(java.util.Optional.empty());
		ResponseStatusException e = Assertions.assertThrows(ResponseStatusException.class, () -> productsService.getProduct(PRODUCT_ID));

		Assertions.assertEquals("404 NOT_FOUND \"Can't find this product\"", e.getMessage());
	}

}
