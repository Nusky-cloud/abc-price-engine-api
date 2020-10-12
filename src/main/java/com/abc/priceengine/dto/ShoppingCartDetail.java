package com.abc.priceengine.dto;

import lombok.Data;

@Data
public class ShoppingCartDetail {
	
	private Long productId;
	private String productName;
	private Float productPrice;
}
