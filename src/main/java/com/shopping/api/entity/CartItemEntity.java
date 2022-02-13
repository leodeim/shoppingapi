package com.shopping.api.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@Table(name = "cart_items")
public class CartItemEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cart_item_id")
	private Long id;

	@ManyToOne(optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name="product")
	private ProductEntity product;

	@Column(nullable = false)
	private int quantity;

	public CartItemEntity(ProductEntity productEntity, int quantity) {
		this.product = productEntity;
		this.quantity = quantity;
	}

	public BigDecimal getFullPrice() {
		return this.product.getPrice().multiply(new BigDecimal(this.quantity));
	}
}
