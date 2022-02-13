package com.shopping.api.controller;

import com.shopping.api.domain.dto.ProductResponseTO;
import com.shopping.api.domain.dto.ProductTO;
import com.shopping.api.service.ProductsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
public class ProductsController implements ApiV1Controller {
	private final ProductsService productsService;

	@ApiOperation(value = "Get all products in the store")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success")
	})
	@GetMapping(value = {"/product/all"})
	@ResponseStatus(value = HttpStatus.OK)
	public List<ProductResponseTO> getAllProducts() {
		return productsService.getProducts();
	}

	@ApiOperation(value = "Get product by product ID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not found")
	})
	@GetMapping(value = {"/product/{id}"})
	@ResponseStatus(value = HttpStatus.OK)
	public ProductResponseTO getProduct(@PathVariable Long id) {
		return productsService.getProduct(id);
	}

	@ApiOperation(value = "Add new product")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created")
	})
	@PostMapping(value = {"/product"})
	@ResponseStatus(value = HttpStatus.CREATED)
	public void addProduct(@RequestBody @Valid ProductTO productTO) {
		productsService.addProduct(productTO);
	}

	@ApiOperation(value = "Delete product by product ID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not found")
	})
	@DeleteMapping(value = {"/product/{id}"})
	@ResponseStatus(value = HttpStatus.OK)
	public void deleteProduct(@PathVariable Long id) {
		productsService.deleteProduct(id);
	}
}
