package com.shopping.api.service;

import com.shopping.api.domain.dto.ProductResponseTO;
import com.shopping.api.entity.ProductEntity;
import com.shopping.api.domain.dto.ProductTO;
import com.shopping.api.repo.ProductsRepository;
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
public class ProductsService {
	private final ProductsRepository productsRepository;

	public ProductEntity addProduct(ProductTO newProduct) {
		var productEntity = productsRepository.save(new ProductEntity(newProduct));
		log.info("New product has been added: {}", productEntity);
		return productEntity;
	}

	public void deleteProduct(Long productId) {
		var product = productsRepository.findById(productId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find this product"));

		productsRepository.delete(product);
		log.info("Product has been deleted: {}", product);
	}

	public List<ProductResponseTO> getProducts() {
		var entities = productsRepository.findAll();
		return entities.stream().map(ProductResponseTO::new).collect(Collectors.toList());
	}

	public ProductResponseTO getProduct(Long productId) {
		var entity = productsRepository.findById(productId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find this product"));
		return new ProductResponseTO(entity);
	}
}
