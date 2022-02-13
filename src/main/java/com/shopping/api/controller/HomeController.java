package com.shopping.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RequiredArgsConstructor
@RestController
@CrossOrigin
@ApiIgnore
public class HomeController {

	@GetMapping
	public void goToSwagger(HttpServletResponse httpResponse) throws IOException {
		httpResponse.sendRedirect("/swagger-ui/");
	}
}
