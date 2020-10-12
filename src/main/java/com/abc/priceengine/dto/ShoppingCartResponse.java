package com.abc.priceengine.dto;

import java.util.List;

import lombok.Data;

@Data
public class ShoppingCartResponse {
	
	private List<ShoppingCartDetail> pricesByProduct;
	private Float totalPrice;
}
