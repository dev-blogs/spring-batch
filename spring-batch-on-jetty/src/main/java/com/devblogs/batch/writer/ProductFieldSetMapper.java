package com.devblogs.batch.writer;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;
import com.devblogs.model.Product;

public class ProductFieldSetMapper implements FieldSetMapper<Product> {
	public Product mapFieldSet(FieldSet fieldSet) throws BindException {
		Product product = new Product();
		product.setId(fieldSet.readString("ID"));
		product.setName(fieldSet.readString("NAME"));
		product.setDescription(fieldSet.readString("DESCRIPTION"));
		product.setPrice(fieldSet.readBigDecimal("PRICE"));
		return product;
	}
}