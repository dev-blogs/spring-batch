package com.example.repository.jdbc;

import com.example.exceptions.DublicateKeyException;
import com.example.integration.ProductImport;

public interface ProductImportRepository {
	void createProductImport(String importId) throws DublicateKeyException;
	void mapImportToJobInstance(String importId, Long jobInstanceId);
	ProductImport get(String importId);
}