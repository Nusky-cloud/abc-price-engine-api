package com.abc.priceengine.testing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.abc.priceengine.dto.ProductDTO;
import com.abc.priceengine.dto.ShoppingCartRequest;
import com.abc.priceengine.dto.ShoppingCartResponse;
import com.abc.priceengine.entity.Product;
import com.abc.priceengine.repository.ProductRepository;
import com.abc.priceengine.service.ProductService;
import com.abc.priceengine.service.impl.ProductServiceImpl;
import com.abc.priceengine.util.ProductQuantityType;

@RunWith(SpringRunner.class)
public class ProductServiceImplIntegrationTest {
	
	@TestConfiguration
	static class ProductServiceImplTestContextCongiuration {
		
		@Bean
		public ProductService productService() {
			return new ProductServiceImpl();
		}
	}
	
	@Autowired
	private ProductService productService;
	
	@MockBean
	private ProductRepository productRepository;
	
	@Before
	public void setup() {
		List<Product> products = new ArrayList<>();
		
		Product product1 = new Product();
		product1.setProductId(1l);
		product1.setProductName("Penguin-ears");
		product1.setProductDescription("Penguin-ears");
		product1.setCartonPrice(175.00f);
		product1.setUnitQuantityPerCarton(20);
		products.add(product1);
		
		Product product2 = new Product();
		product2.setProductId(2l);
		product2.setProductName("Horseshoe");
		product2.setProductDescription("Horseshoe");
		product2.setCartonPrice(825.00f);
		product2.setUnitQuantityPerCarton(5);
		products.add(product2);
		
		Mockito.when(productRepository.findAll())
				.thenReturn(products);
	}
	
	@Test
	public void whenGetAllProducts_thenReturnAllProducts() {
		List<ProductDTO> productDTOs = productService.getAllProducts();
		Assertions.assertThat(productDTOs.size()).isEqualTo(2);
	}
	
	@Test
	public void whenCalculatePriceWithNoProductId_thenReturnNull() {
		ShoppingCartRequest shoppingCartData = new ShoppingCartRequest();
		List<ShoppingCartRequest> shoppingCartRequest = Arrays.asList(shoppingCartData);
		ShoppingCartResponse shoppingCartResponse = productService.calculatePrice(shoppingCartRequest);
		Assertions.assertThat(shoppingCartResponse).isNull();
	}
	
	@Test
	public void whenCalculatePriceWithNonExistentProduct_thenReturnNull() {
		ShoppingCartRequest shoppingCartData = new ShoppingCartRequest();
		shoppingCartData.setProductId(1000l);
		List<ShoppingCartRequest> shoppingCartRequest = Arrays.asList(shoppingCartData);
		ShoppingCartResponse shoppingCartResponse = productService.calculatePrice(shoppingCartRequest);
		Assertions.assertThat(shoppingCartResponse).isNull();
	}
	
	@Test
	public void whenCalculatePriceWithEmptyQuantitites_thenReturnNull() {
		ShoppingCartRequest shoppingCartData = new ShoppingCartRequest();
		shoppingCartData.setProductId(1l);
		shoppingCartData.setQuantitiesByType(new HashMap<ProductQuantityType, Integer>());
		List<ShoppingCartRequest> shoppingCartRequest = Arrays.asList(shoppingCartData);
		ShoppingCartResponse shoppingCartResponse = productService.calculatePrice(shoppingCartRequest);
		Assertions.assertThat(shoppingCartResponse).isNull();
	}
}
