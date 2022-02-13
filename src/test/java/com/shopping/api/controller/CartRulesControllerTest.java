package com.shopping.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.api.domain.CartRuleType;
import com.shopping.api.domain.dto.CartRuleResponseTO;
import com.shopping.api.domain.dto.CartRuleTO;
import com.shopping.api.service.CartRulesService;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {CartRulesController.class})
@AutoConfigureMockMvc
@EnableWebMvc
class CartRulesControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CartRulesService cartRulesService;

	@Test
	void getAllRulesTest() throws Exception {
		List<CartRuleResponseTO> list = new ArrayList<>();
		Mockito.when(cartRulesService.getAllRules()).thenReturn(list);
		mockMvc.perform(get("/api/v1/cart/rule/all"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());
	}

	@Test
	void addRuleTest() throws Exception {
		var ruleTO = new CartRuleTO(CartRuleType.ORDER_LIMIT, new BigDecimal(100), null, null, null);
		var mapper = new ObjectMapper();
		var jsonObject = mapper.writeValueAsString(ruleTO);

		mockMvc.perform(post("/api/v1/cart/rule").content(jsonObject).contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isCreated());

		Mockito.verify(cartRulesService).addCartRule(ruleTO);
	}

	@Test
	void deleteRuleTest() throws Exception {
		mockMvc.perform(delete("/api/v1/cart/rule/1"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());

		Mockito.verify(cartRulesService).deleteCartRule(1L);
	}
}
