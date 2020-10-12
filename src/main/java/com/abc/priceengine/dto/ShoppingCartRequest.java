package com.abc.priceengine.dto;

import java.util.Map;

import com.abc.priceengine.util.ProductQuantityType;

import lombok.Data;

@Data
public class ShoppingCartRequest {
	
	private Long productId;
	private Map<ProductQuantityType, Integer> quantitiesByType;
}
