package com.abc.priceengine.testing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.abc.priceengine.entity.Product;
import com.abc.priceengine.repository.ProductRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProductRepositoryIntegrationTest {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Test
	public void whenFindByEmptyList_thenReturnEmptyList() {
		List<Product> products = productRepository.findByProductIdIn(new ArrayList<>());
		Assertions.assertThat(products).isEmpty();
	}
	
	@Test
	public void whenFindByNonExistentIds_thenReturnEmptyList() {
		List<Product> products = productRepository.findByProductIdIn(Arrays.asList(1000l, 1001l));
		Assertions.assertThat(products).isEmpty();
	}
	
	@Test
	public void whenFindByNonExistentIdWithExistentId_thenReturnOneProduct() {
		List<Product> products = productRepository.findByProductIdIn(Arrays.asList(1l, 1000l));
		Assertions.assertThat(products.size()).isEqualTo(1);
		Assertions.assertThat(products.get(0).getProductId()).isEqualTo(1l);
	}
	
	@Test
	public void whenFindBySameExistentId_thenReturnOneProductOnly() {
		List<Product> products = productRepository.findByProductIdIn(Arrays.asList(1l, 1l));
		Assertions.assertThat(products.size()).isEqualTo(1);
		Assertions.assertThat(products.get(0).getProductId()).isEqualTo(1l);
	}
	
	@Test
	public void whenFindByExistentIds_thenReturnProducts() {
		List<Long> productIds = Arrays.asList(1l, 2l);
		List<Product> products = productRepository.findByProductIdIn(productIds);
		Assertions.assertThat(products.size()).isEqualTo(productIds.size());
		Assertions.assertThat(products.get(0).getProductId()).isEqualTo(1l);
		Assertions.assertThat(products.get(1).getProductId()).isEqualTo(2l);
	}
}
