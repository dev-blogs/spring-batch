package com.example.repository.jdbc;

import java.util.Date;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.example.exceptions.DublicateKeyException;
import com.example.integration.ProductImport;

@Repository
@Transactional
public class JdbcProductImportRepository implements ProductImportRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private JobExplorer jobExplorer;
	
	@Override
	public void createProductImport(String importId) throws DublicateKeyException {
		int count = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM PRODUCT_IMPORT WHERE IMPORT_ID = ?", new Object[] { importId }, Integer.class);
		if (count > 0) {
			throw new DublicateKeyException("Import already exists: " + importId);
		} else {
			jdbcTemplate.update("INSERT INTO PRODUCT_IMPORT (IMPORT_ID, CREATION_DATE) VALUES (?, ?)", importId, new Date());
		}
	}

	@Override
	public void mapImportToJobInstance(String importId, Long jobInstanceId) {
		jdbcTemplate.update("UPDATE PRODUCT_IMPORT SET JOB_INSTANCE_ID = ? WHERE IMPORT_ID = ?", jobInstanceId, importId);
	}
	
	@Override
	public ProductImport get(String importId) {
		int count = jdbcTemplate.queryForInt("SELECT COUNT(1) FROM PRODUCT_IMPORT WHERE IMPORT_ID = ?", importId);
		if (count == 0) {
			throw new EmptyResultDataAccessException("No import with this ID: " + importId, 1);
		}
		String status = "PENDING";
		Long instanceId = jdbcTemplate.queryForLong("SELECT JOB_INSTANCE_ID FROM PRODUCT_IMPORT WHERE IMPORT_ID = ?", importId);
		JobInstance jobInstance = jobExplorer.getJobInstance(instanceId);
		if (jobInstance != null) {
			JobExecution lastJobExecution = jobExplorer.getJobExecutions(jobInstance).get(0);
			status = lastJobExecution.getStatus().toString();
		}
		return new ProductImport(importId, status);
	}
}