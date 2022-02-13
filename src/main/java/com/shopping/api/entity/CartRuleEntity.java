package com.shopping.api.entity;

import com.shopping.api.domain.CartRuleType;
import com.shopping.api.domain.dto.CartRuleTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@Table(name = "cart_rules")
public class CartRuleEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cart_rule_id")
	private Long id;

	@Column(nullable = false)
	private CartRuleType type;

	@Column
	private BigDecimal orderLimit;

	@Column
	private BigDecimal orderSum;

	@Column
	private BigDecimal discount;

	@Column
	private Integer discountedProductNumber;

	public CartRuleEntity(CartRuleTO cartRuleTO) {
		this.type = cartRuleTO.getType();
		this.orderLimit = cartRuleTO.getOrderLimit();
		this.orderSum = cartRuleTO.getOrderSum();
		this.discount = cartRuleTO.getDiscount();
		this.discountedProductNumber = cartRuleTO.getDiscountedProductNumber();
	}
}
