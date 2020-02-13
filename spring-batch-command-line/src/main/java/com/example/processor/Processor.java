package com.example.processor;

import org.springframework.batch.item.ItemProcessor;

import com.example.model.Product;

public class Processor implements ItemProcessor<Product, Product> {
	@Override
	public Product process(Product item) throws Exception {
		return item;
	}
}