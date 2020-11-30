package com.example.repository.jdbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"/applicationContextTest.xml" 
	})
public class ImportProductJobTest {
	@Autowired
	private Job job;
	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Test
	public void test() throws Exception {
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("importId", "1234")
				.addString("importFile", "classpath:test-data/products.xml")
				.addLong("timestamp", System.currentTimeMillis())
				.toJobParameters();
		
		jobLauncher.run(job, jobParameters);
		
		assertEquals(1, jdbcTemplate.queryForInt("SELECT count(*) FROM products"));
	}
}