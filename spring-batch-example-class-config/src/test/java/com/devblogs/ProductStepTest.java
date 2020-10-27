package com.devblogs;

import static org.junit.Assert.assertEquals;
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
import com.devblogs.config.Config;
import com.devblogs.config.DatabaseConfig;
import com.devblogs.config.InfrustructureConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Config.class, DatabaseConfig.class, InfrustructureConfig.class })
public class ProductStepTest {
	@Autowired
	private Job job;
	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Test
	//@DirtiesContext
	public void testIntegration() throws Exception {
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("inputResource", "input/products.zip")
				.addString("targetDirectory", "target/importproductsbatch/")
				.addString("targetFile", "products.txt")
				.addLong("timestamp", System.currentTimeMillis())
				.toJobParameters();
		
		jobLauncher.run(job, jobParameters);
		
		assertEquals(3, jdbcTemplate.queryForObject("SELECT count(*) FROM products", Integer.class).intValue());
	}
}