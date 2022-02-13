package com.shopping.api.controller;

import com.shopping.api.domain.dto.CartSummaryTO;
import com.shopping.api.domain.dto.CartItemResponseTO;
import com.shopping.api.domain.dto.CartItemTO;
import com.shopping.api.service.CartItemService;
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
public class CartController implements ApiV1Controller {
	private final CartItemService cartItemService;

	@ApiOperation(value = "Get all items which are in the cart")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success")
	})
	@GetMapping(value = {"cart"})
	@ResponseStatus(value = HttpStatus.OK)
	public List<CartItemResponseTO> getAllCartItems() {
		return cartItemService.getAllItems();
	}

	@ApiOperation(value = "Add products to the cart")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created"),
			@ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 406, message = "Duplicate"),
			@ApiResponse(code = 409, message = "Limit is reached")
	})
	@PostMapping(value = {"cart/item"})
	@ResponseStatus(value = HttpStatus.CREATED)
	public CartSummaryTO addCartItem(@RequestBody @Valid CartItemTO cartItemTO) {
		return cartItemService.addToCart(cartItemTO);
	}

	@ApiOperation(value = "Delete product from the cart by cart item ID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not found")
	})
	@DeleteMapping(value = {"cart/item/{id}"})
	@ResponseStatus(value = HttpStatus.OK)
	public void deleteCartItem(@PathVariable Long id) {
		cartItemService.deleteItem(id);
	}

	@ApiOperation(value = "Delete all products from the cart")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success")
	})
	@DeleteMapping(value = {"cart"})
	@ResponseStatus(value = HttpStatus.OK)
	public void emptyCart() {
		cartItemService.emptyCart();
	}

	@ApiOperation(value = "Calculate card summary")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 409, message = "Limit is reached")
	})
	@GetMapping(value = {"cart/calculations"})
	@ResponseStatus(value = HttpStatus.OK)
	public CartSummaryTO getCartItemsCalculation() {
		return cartItemService.getCalculations();
	}
}
