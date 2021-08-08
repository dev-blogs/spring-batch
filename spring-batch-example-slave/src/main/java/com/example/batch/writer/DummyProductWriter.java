package com.example.batch.writer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.batch.item.ItemWriter;
import com.example.model.Product;
import com.example.utils.ThreadUtils;

public class DummyProductWriter implements ItemWriter<Product> {
	
	private List<Product> products = new ArrayList<Product>();

	@Override
	public void write(List<? extends Product> items) throws Exception {
		ThreadUtils.writeThreadExecutionMessage("write", items);
		for(Product product : items) {
			processProduct(product);
		}
	}

	private void processProduct(Product product) throws InterruptedException {
		Thread.sleep(5);
		synchronized(products) {
			products.add(product);
		}
	}
	
	public List<Product> getProducts() {
		return Collections.unmodifiableList(products);
	}
	
	public void clear() {
		products.clear();
	}
}