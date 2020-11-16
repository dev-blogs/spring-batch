package com.example.repository.jdbc;

import com.example.exceptions.DublicateKeyException;

public interface ProductImportRepository {
	void createProductImport(String importId) throws DublicateKeyException;
}