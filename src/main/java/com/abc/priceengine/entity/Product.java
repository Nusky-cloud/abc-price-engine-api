package com.abc.priceengine.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long productId;
	private String productName;
	private String productDescription;
	private Float cartonPrice;
	private Integer unitQuantityPerCarton;
}
