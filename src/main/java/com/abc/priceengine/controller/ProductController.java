package com.abc.priceengine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abc.priceengine.dto.ProductDTO;
import com.abc.priceengine.dto.ShoppingCartRequest;
import com.abc.priceengine.dto.ShoppingCartResponse;
import com.abc.priceengine.protocol.ResponseEnvelope;
import com.abc.priceengine.service.ProductService;

@CrossOrigin
@RestController
@RequestMapping("/price-engine")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping
	public ResponseEntity<ResponseEnvelope<List<ProductDTO>>> getAllProducts() {
		ResponseEnvelope<List<ProductDTO>> responseEnvelope = null;
		List<ProductDTO> productDTOs = productService.getAllProducts();
		
		if (!productDTOs.isEmpty()) {
			responseEnvelope = new ResponseEnvelope<>(HttpStatus.OK, "All products found!", productDTOs);
		} else {
			responseEnvelope = new ResponseEnvelope<>(HttpStatus.NO_CONTENT, "No products found!");
		}
		
		return new ResponseEntity<>(responseEnvelope, responseEnvelope.getStatus());
	}
	
	@PostMapping("/calculatePrice")
	public ResponseEntity<ResponseEnvelope<ShoppingCartResponse>> calculatePrice(@RequestBody List<ShoppingCartRequest> shoppingCartRequest) {
		ResponseEnvelope<ShoppingCartResponse> responseEnvelope = null;
		
		if (!shoppingCartRequest.isEmpty()) {
			ShoppingCartResponse shoppingCartResponse = productService.calculatePrice(shoppingCartRequest);
			
			if (shoppingCartResponse != null) {
				responseEnvelope = new ResponseEnvelope<>(HttpStatus.OK, "Price calculated!", shoppingCartResponse);
			} else {
				responseEnvelope = new ResponseEnvelope<>(HttpStatus.BAD_REQUEST, "Please try again later!");
			}
		} else {
			responseEnvelope = new ResponseEnvelope<>(HttpStatus.BAD_REQUEST, "Please send product quantities!");
		}
		
		return new ResponseEntity<>(responseEnvelope, responseEnvelope.getStatus());
	}
}
