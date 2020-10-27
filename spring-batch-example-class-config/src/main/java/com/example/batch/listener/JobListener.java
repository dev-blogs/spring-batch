package com.example.batch.listener;

import java.util.List;
import java.util.Map;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.jdbc.core.JdbcTemplate;

public class JobListener implements JobExecutionListener {
	private JdbcTemplate jdbcTemplate;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		List<Map<String, Object>> products = jdbcTemplate.queryForList("SELECT * FROM products");
		for (Map<String, Object> product : products) {
			System.out.println(product);
		}
	}
}