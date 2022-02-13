package com.shopping.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.api.domain.ProductCategory;
import com.shopping.api.domain.dto.ProductResponseTO;
import com.shopping.api.domain.dto.ProductTO;
import com.shopping.api.entity.ProductEntity;
import com.shopping.api.service.ProductsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ProductsController.class})
@AutoConfigureMockMvc
@EnableWebMvc
class ProductsControllerTest {
	private static String TEST_STRING = "test_string";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductsService productsService;

	@Test
	void getAllProductsTest() throws Exception {
		List<ProductResponseTO> list = new ArrayList<>();
		Mockito.when(productsService.getProducts()).thenReturn(list);
		mockMvc.perform(get("/api/v1/product/all"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());
	}

	@Test
	void getProductTest() throws Exception {
		var product = new ProductResponseTO();
		Mockito.when(productsService.getProduct(1L)).thenReturn(product);
		mockMvc.perform(get("/api/v1/product/1"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());
	}

	@Test
	void addProductTest() throws Exception {
		var productTO = new ProductTO(TEST_STRING, ProductCategory.BEVERAGES, BigDecimal.valueOf(999));
		var mapper = new ObjectMapper();
		var jsonObject = mapper.writeValueAsString(productTO);

		mockMvc.perform(post("/api/v1/product").content(jsonObject).contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isCreated());

		Mockito.verify(productsService).addProduct(productTO);
	}

	@Test
	void deleteProductTest() throws Exception {
		mockMvc.perform(delete("/api/v1/product/1"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());

		Mockito.verify(productsService).deleteProduct(1L);
	}
}
