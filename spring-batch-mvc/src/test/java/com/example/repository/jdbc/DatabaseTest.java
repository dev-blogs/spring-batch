package com.example.repository.jdbc;

import static org.junit.Assert.assertNotEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.example.exceptions.DublicateKeyException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"/applicationContextTest.xml" 
	})
public class DatabaseTest {
	@Autowired
	private ProductImportRepository productImportRepository;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Test
	public void testInsert() throws DublicateKeyException {
		productImportRepository.createProductImport("test");
		
		int count = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM PRODUCT_IMPORT WHERE IMPORT_ID = 'test'", Integer.class);
		
		assertNotEquals(0, count);
	}
}