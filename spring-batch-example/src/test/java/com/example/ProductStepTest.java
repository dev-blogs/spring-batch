package com.example;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
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
	private Job job;
	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private SimpleJdbcTemplate simpleJdbcTemplate;
	@Autowired
	private JobExplorer jobExplorer;
	
	@Test
	@DirtiesContext
	public void testIntegration() throws Exception {
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("inputResource", "classpath:/input/products.zip")
				.addString("targetDirectory", "./target/importproductsbatch/")
				.addString("targetFile", "products.txt")
				.addLong("timestamp", System.currentTimeMillis())
				.toJobParameters();
		
		Executors.newSingleThreadExecutor().submit(() -> jobLauncher.run(job, jobParameters));
		
		List<JobExecution> runningJobInstances = new ArrayList<>();
		List<String> jobNames = jobExplorer.getJobNames();
		for (String jobName : jobNames) {
			Set<JobExecution> jobExecutions = jobExplorer.findRunningJobExecutions(jobName);
			runningJobInstances.addAll(jobExecutions);
		}
		
		System.out.println("jobNames: " + jobNames.size());
		System.out.println("running job instances: " + runningJobInstances.size());
		
		assertEquals(5, simpleJdbcTemplate.queryForInt("SELECT count(*) FROM products"));
	}
}