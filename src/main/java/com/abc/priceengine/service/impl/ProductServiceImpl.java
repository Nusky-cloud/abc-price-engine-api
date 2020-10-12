package com.abc.priceengine.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.priceengine.converter.ProductConverter;
import com.abc.priceengine.dto.ProductDTO;
import com.abc.priceengine.dto.ShoppingCartDetail;
import com.abc.priceengine.dto.ShoppingCartRequest;
import com.abc.priceengine.dto.ShoppingCartResponse;
import com.abc.priceengine.entity.Product;
import com.abc.priceengine.repository.ProductRepository;
import com.abc.priceengine.service.ProductService;
import com.abc.priceengine.util.CommonUtil;
import com.abc.priceengine.util.ProductQuantityType;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public List<ProductDTO> getAllProducts() {
		List<ProductDTO> allProductDTOs = new ArrayList<>();
		List<Product> allProducts = productRepository.findAll();
		
		if (allProducts != null && !allProducts.isEmpty()) {
			allProducts.forEach(product -> {
				allProductDTOs.add(ProductConverter.convertProductEntityToDTO(product));
			});
		}
		
		return allProductDTOs;
	}

	@Override
	public ShoppingCartResponse calculatePrice(List<ShoppingCartRequest> shoppingCartRequest) {
		List<Long> productIds = shoppingCartRequest.stream()
													.map(ShoppingCartRequest::getProductId)
													.collect(Collectors.toList());
		if (productIds.isEmpty()) {
			return null;
		}
		
		List<Product> productList = productRepository.findByProductIdIn(productIds);
		if (productList.isEmpty()) {
			return null;
		}
		
		List<ShoppingCartDetail> pricesByProduct = new ArrayList<>();
		shoppingCartRequest.forEach(product -> {
			Product productDetails = productList.stream()
												.filter(productDetail -> productDetail.getProductId() == product.getProductId())
												.findFirst()
												.orElse(null);
			if (productDetails != null) {
				Map<ProductQuantityType, Integer> quantitiesByType = product.getQuantitiesByType();
				if (quantitiesByType != null && !quantitiesByType.isEmpty()) {
					ShoppingCartDetail shoppingCartDetail = new ShoppingCartDetail();
					Integer noOfUnitsPurchased = 0;
					Integer noOfCartonsPurchased = 0;
					Float totalUnitPrice = 0.0f;
					Float totalCartonPrice = 0.0f;
					
					noOfUnitsPurchased += quantitiesByType.getOrDefault(ProductQuantityType.UNIT, 0);
					noOfCartonsPurchased += quantitiesByType.getOrDefault(ProductQuantityType.CARTON, 0);
					
					if (noOfUnitsPurchased >= productDetails.getUnitQuantityPerCarton()) {
						noOfCartonsPurchased += (noOfUnitsPurchased / productDetails.getUnitQuantityPerCarton());
						noOfUnitsPurchased = noOfUnitsPurchased % productDetails.getUnitQuantityPerCarton();
					}
					
					if (noOfUnitsPurchased > 0) {
						totalUnitPrice = noOfUnitsPurchased * CommonUtil.getUnitPrice(productDetails.getCartonPrice(), productDetails.getUnitQuantityPerCarton());
					}
					
					if (noOfCartonsPurchased > 0) {
						totalCartonPrice = noOfCartonsPurchased * productDetails.getCartonPrice();
						if (noOfCartonsPurchased >= 3) {
							totalCartonPrice = totalCartonPrice * 0.9f;
						}
					}
					
					shoppingCartDetail.setProductId(productDetails.getProductId());
					shoppingCartDetail.setProductName(productDetails.getProductName());
					shoppingCartDetail.setProductPrice(totalUnitPrice + totalCartonPrice);
					pricesByProduct.add(shoppingCartDetail);
				}
			}
		});
		
		if (!pricesByProduct.isEmpty()) {
			ShoppingCartResponse shoppingCartResponse = new ShoppingCartResponse();
			shoppingCartResponse.setPricesByProduct(pricesByProduct);
			shoppingCartResponse.setTotalPrice((float) pricesByProduct.stream().mapToDouble(ShoppingCartDetail::getProductPrice).sum());
			return shoppingCartResponse;
		}
		
		return null;
	}

}
