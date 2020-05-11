package com.example;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"/spring/import-products-job-context.xml", 
		"/spring/infrustructure-context.xml" 
	})
public class ProductStepTest {
	@Autowired
	private JobRegistry jobRegistry;
	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private JobOperator jobOperator;
	@Autowired
	private SimpleJdbcTemplate simpleJdbcTemplate;
	
	@Test
	@DirtiesContext
	public void testIntegration() throws Exception {
		Job job = jobRegistry.getJob("importProducts");
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("inputResource", "classpath:/input/products.zip")
				.addString("targetDirectory", "./target/importproductsbatch/")
				.addString("targetFile", "products.txt")
				.addLong("timestamp", System.currentTimeMillis())
				.toJobParameters();
		
		jobLauncher.run(job, jobParameters);
		String name = simpleJdbcTemplate.queryForObject("SELECT name FROM products WHERE ID = 1", String.class);
		assertEquals("BlackBerry 8100 Pearl", name);
		
		jobOperator.startNextInstance("importProducts");
		name = simpleJdbcTemplate.queryForObject("SELECT name FROM products WHERE ID = 1", String.class);
		assertEquals("test1", name);
		
		assertEquals(5, simpleJdbcTemplate.queryForInt("SELECT count(*) FROM products"));
	}
}