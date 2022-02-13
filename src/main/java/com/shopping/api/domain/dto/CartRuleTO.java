package com.shopping.api.domain.dto;

import com.shopping.api.domain.CartRuleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartRuleTO {
	@NotNull
	private CartRuleType type;
	private BigDecimal orderLimit;
	private BigDecimal orderSum;
	private BigDecimal discount;
	private Integer discountedProductNumber;
}
