package com.devblogs.batch.processor;

import org.springframework.batch.item.ItemProcessor;
import com.devblogs.model.Product;

public class Processor implements ItemProcessor<Product, Product> {
	@Override
	public Product process(Product item) throws Exception {
		return item;
	}
}