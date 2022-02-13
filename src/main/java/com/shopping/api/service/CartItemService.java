package com.shopping.api.service;

import com.shopping.api.domain.dto.CartSummaryTO;
import com.shopping.api.domain.dto.CartItemResponseTO;
import com.shopping.api.entity.ProductEntity;
import com.shopping.api.entity.CartItemEntity;
import com.shopping.api.domain.dto.CartItemTO;
import com.shopping.api.repo.CartItemsRepository;
import com.shopping.api.repo.ProductsRepository;
import com.shopping.api.processor.CartRulesProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartItemService {
	private final CartItemsRepository cartItemsRepository;
	private final ProductsRepository productsRepository;
	private final CartRulesProcessor cartRulesProcessor;

	public void emptyCart() {
		cartItemsRepository.deleteAll();
		log.info("Cart has been emptied");
	}

	public List<CartItemResponseTO> getAllItems() {
		var entities = cartItemsRepository.findAll();
		return entities.stream().map(CartItemResponseTO::new).collect(Collectors.toList());
	}

	@Transactional(rollbackFor = ResponseStatusException.class)
	public CartSummaryTO addToCart(CartItemTO cartItemRequest) {
		var product = this.getProduct(cartItemRequest.getProductId());
		var newItem = getCartItemEntity(product, cartItemRequest.getQuantity());

		cartItemsRepository.save(newItem);
		var cartSummary = this.getCalculations();

		if (cartSummary.isLimitReached()) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Order limit has been reached");
		}

		log.info("New item has been added to card: {}", cartItemRequest);
		return cartSummary;
	}

	public void deleteItem(Long itemId) {
		var itemObject = cartItemsRepository.findById(itemId);

		itemObject.map(item -> {
			cartItemsRepository.delete(item);
			log.info("Item has been deleted from card: {}", item);
			return 0;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find this item"));
	}

	public CartSummaryTO getCalculations() {
		return cartRulesProcessor.process(cartItemsRepository.findAll());
	}

	private ProductEntity getProduct(Long productId) {
		return productsRepository.findById(productId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find this product"));
	}

	private CartItemEntity getCartItemEntity(ProductEntity product, int newQuantity) {
		var entityObject = cartItemsRepository.findByProduct(product);

		if (entityObject.isPresent()) {
			var entity = entityObject.get();
			entity.setQuantity(newQuantity);
			return entity;
		}
		return new CartItemEntity(product, newQuantity);
	}
}
