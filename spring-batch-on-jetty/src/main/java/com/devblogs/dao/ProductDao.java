package com.devblogs.dao;

import com.devblogs.model.Product;

public interface ProductDao {
	int saveProduct(Product product);
	Product findProductByName(String product);
}