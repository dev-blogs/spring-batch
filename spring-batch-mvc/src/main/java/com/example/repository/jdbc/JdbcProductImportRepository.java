package com.example.repository.jdbc;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.example.exceptions.DublicateKeyException;

@Repository
@Transactional
public class JdbcProductImportRepository implements ProductImportRepository {
	private static final String SQL_SELECT = "SELECT COUNT(1) FROM PRODUCT_IMPORT WHERE IMPORT_ID = ?";
	private static final String SQL_INSERT = "INSERT INTO PRODUCT_IMPORT (IMPORT_ID, CREATION_DATE) VALUES (?, ?)";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void createProductImport(String importId) throws DublicateKeyException {
		int count = jdbcTemplate.queryForObject(SQL_SELECT, new Object[] { importId }, Integer.class);
		if (count > 0) {
			throw new DublicateKeyException("Imoprt already exists: " + importId);
		} else {
			jdbcTemplate.update(SQL_INSERT, importId, new Date());
		}
	}
}