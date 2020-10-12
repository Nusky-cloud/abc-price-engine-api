package com.abc.priceengine.dto;

import lombok.Data;

@Data
public class ProductDTO {
	
	private Long productId;
	private String productName;
	private String productDescription;
	private Float cartonPrice;
	private Integer unitQuantityPerCarton;
	private Float unitPrice;
}
