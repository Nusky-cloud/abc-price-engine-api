package com.abc.priceengine.converter;

import com.abc.priceengine.dto.ProductDTO;
import com.abc.priceengine.entity.Product;
import com.abc.priceengine.util.CommonUtil;

public class ProductConverter {
	
	public static ProductDTO convertProductEntityToDTO(Product product) {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setProductId(product.getProductId());
		productDTO.setProductName(product.getProductName());
		productDTO.setProductDescription(product.getProductDescription());
		productDTO.setCartonPrice(product.getCartonPrice());
		productDTO.setUnitQuantityPerCarton(product.getUnitQuantityPerCarton());
		productDTO.setUnitPrice(CommonUtil.getUnitPrice(product.getCartonPrice(), product.getUnitQuantityPerCarton()));
		return productDTO;
	}
}
