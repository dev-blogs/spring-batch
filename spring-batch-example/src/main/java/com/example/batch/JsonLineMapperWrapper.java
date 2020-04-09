package com.example.batch;

import java.math.BigDecimal;
import java.util.Map;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.JsonLineMapper;
import com.example.model.Product;

public class JsonLineMapperWrapper implements LineMapper<Product> {
	private JsonLineMapper delegate;
	
	public void setDelegate(JsonLineMapper delegate) {
		this.delegate = delegate;
	}

	@Override
	public Product mapLine(String line, int lineNumber) throws Exception {
		Map<String, Object> map = delegate.mapLine(line, lineNumber);
		Product product = new Product();
		product.setId((String) map.get("id"));
		product.setName((String) map.get("name"));
		product.setDescription((String) map.get("description"));
		product.setPrice(new BigDecimal((double) map.get("price")));
		return product;
	}
}