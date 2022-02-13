package com.shopping.api.controller;

import com.shopping.api.domain.dto.CartRuleResponseTO;
import com.shopping.api.domain.dto.CartRuleTO;
import com.shopping.api.service.CartRulesService;
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
public class CartRulesController implements ApiV1Controller {
	private final CartRulesService cartRulesService;

	@ApiOperation(value = "Get all cart rules")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success")
	})
	@GetMapping(value = {"/cart/rule/all"})
	@ResponseStatus(value = HttpStatus.OK)
	public List<CartRuleResponseTO> getAllCartRules() {
		return cartRulesService.getAllRules();
	}

	@ApiOperation(value = "Add new cart rule")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created")
	})
	@PostMapping(value = {"/cart/rule"})
	@ResponseStatus(value = HttpStatus.CREATED)
	public CartRuleResponseTO addCartRule(@RequestBody @Valid CartRuleTO cartRuleTO) {
		return cartRulesService.addCartRule(cartRuleTO);
	}

	@ApiOperation(value = "Delete cart rule by ID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not found")
	})
	@DeleteMapping(value = {"/cart/rule/{id}"})
	@ResponseStatus(value = HttpStatus.OK)
	public void deleteCartRule(@PathVariable Long id) {
		cartRulesService.deleteCartRule(id);
	}
}
