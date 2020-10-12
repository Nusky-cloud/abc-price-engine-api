package com.abc.priceengine.service;

import java.util.List;

import com.abc.priceengine.dto.ProductDTO;
import com.abc.priceengine.dto.ShoppingCartRequest;
import com.abc.priceengine.dto.ShoppingCartResponse;

public interface ProductService {
	
	public List<ProductDTO> getAllProducts();
	
	public ShoppingCartResponse calculatePrice(List<ShoppingCartRequest> shoppingCartRequest);
}
