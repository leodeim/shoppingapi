package com.shopping.api.service;

import com.shopping.api.domain.dto.CartRuleResponseTO;
import com.shopping.api.domain.dto.CartRuleTO;
import com.shopping.api.entity.CartRuleEntity;
import com.shopping.api.repo.CartRulesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartRulesService {
	private final CartRulesRepository cartRulesRepository;

	public List<CartRuleResponseTO> getAllRules() {
		var entities = cartRulesRepository.findAll();
		return entities.stream().map(CartRuleResponseTO::new).collect(Collectors.toList());
	}

	public CartRuleResponseTO addCartRule(CartRuleTO cartRuleRequest) {
		var cartRuleEntity = cartRulesRepository.save(new CartRuleEntity(cartRuleRequest));
		log.info("New cart rule has been added: {}", cartRuleEntity);
		return new CartRuleResponseTO(cartRuleEntity);
	}

	public void deleteCartRule(Long ruleId) {
		var cartRuleObject = cartRulesRepository.findById(ruleId);

		cartRuleObject.map(item -> {
			cartRulesRepository.delete(item);
			log.info("Cart rule has been deleted: {}", item);
			return 0;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find this cart rule"));
	}
}
