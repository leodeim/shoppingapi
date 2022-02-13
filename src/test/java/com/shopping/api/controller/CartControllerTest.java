package com.shopping.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.api.domain.dto.CartItemResponseTO;
import com.shopping.api.domain.dto.CartItemTO;
import com.shopping.api.service.CartItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {CartController.class})
@AutoConfigureMockMvc
@EnableWebMvc
class CartControllerTest {
	private static Long TEST_ID = 123L;
	private static int TEST_QTY = 5;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CartItemService cartItemService;

	@Test
	void getAllCartItemsTest() throws Exception {
		List<CartItemResponseTO> list = new ArrayList<>();
		Mockito.when(cartItemService.getAllItems()).thenReturn(list);
		mockMvc.perform(get("/api/v1/cart"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());
	}

	@Test
	void addCartItemTest() throws Exception {
		var cartItemTO = new CartItemTO(TEST_ID, TEST_QTY);
		var mapper = new ObjectMapper();
		var jsonObject = mapper.writeValueAsString(cartItemTO);

		mockMvc.perform(post("/api/v1/cart/item").content(jsonObject).contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isCreated());

		Mockito.verify(cartItemService).addToCart(cartItemTO);
	}

	@Test
	void deleteCartItemTest() throws Exception {
		mockMvc.perform(delete("/api/v1/cart/item/1"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());

		Mockito.verify(cartItemService).deleteItem(1L);
	}

	@Test
	void emptyCartTest() throws Exception {
		mockMvc.perform(delete("/api/v1/cart"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());

		Mockito.verify(cartItemService).emptyCart();
	}

	@Test
	void getCartCalculationsTest() throws Exception {
		mockMvc.perform(get("/api/v1/cart/calculations"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());

		Mockito.verify(cartItemService).getCalculations();
	}
}
