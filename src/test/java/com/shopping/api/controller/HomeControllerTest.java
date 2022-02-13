package com.shopping.api.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {HomeController.class})
@AutoConfigureMockMvc
@EnableWebMvc
class HomeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void goToSwaggerTest() throws Exception {
		var response = mockMvc.perform(get("/"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().is3xxRedirection());
	}
}
