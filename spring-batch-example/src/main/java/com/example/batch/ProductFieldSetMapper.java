package com.example.batch;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;
import com.example.model.BookProduct;
import com.example.model.MobilePhoneProduct;
import com.example.model.Product;

public class ProductFieldSetMapper implements FieldSetMapper<Product> {
	public Product mapFieldSet(FieldSet fieldSet) throws BindException {
		String id = fieldSet.readString("ID");
		Product product = null;
		if (id.startsWith("PRB")) {
			product = new BookProduct();
		} else if (id.startsWith("PRM")) {
			product = new MobilePhoneProduct();
		} else {
			product = new Product();
		}
		product.setId(id);
		product.setName(fieldSet.readString("NAME"));
		product.setDescription(fieldSet.readString("DESCRIPTION"));
		product.setPrice(fieldSet.readBigDecimal("PRICE"));
		return product;
	}
}