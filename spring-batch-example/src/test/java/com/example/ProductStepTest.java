package com.example;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
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
		
		jobLauncher.run(job, jobParameters);
		
		List<JobExecution> failedJobExecution = getFailedJobExecution("importProducts");
		//System.out.println("failedJobExecution.size(): " + failedJobExecution.size());
		
		/*List<JobExecution> runningJobInstances = new ArrayList<>();
		List<String> jobNames = jobExplorer.getJobNames();
		for (String jobName : jobNames) {
			Set<JobExecution> jobExecutions = jobExplorer.findRunningJobExecutions(jobName);
			runningJobInstances.addAll(jobExecutions);
		}
		
		System.out.println("jobNames: " + jobNames.size());
		System.out.println("running job instances: " + runningJobInstances.size());*/
		
		assertEquals(5, simpleJdbcTemplate.queryForInt("SELECT count(*) FROM products"));
	}
	
	private List<JobExecution> getFailedJobExecution(String jobName) {
		List<JobExecution> failedJobExecutions = new ArrayList<>();
		
		int pageSize = 10;
		int currentPageSize = 10;
		int currentPage = 0;
		
		while (currentPageSize == pageSize) {
			List<JobInstance> jobInstances = jobExplorer.getJobInstances(jobName, currentPage * pageSize, pageSize);
			currentPageSize = jobInstances.size();
			currentPage++;
			for (JobInstance jobInstance : jobInstances) {
				List<JobExecution> jobExecutions = jobExplorer.getJobExecutions(jobInstance);
				for (JobExecution jobExecution : jobExecutions) {
					/*if (jobExecution.getExitStatus().equals(ExitStatus.FAILED)) {
						failedJobExecutions.add(jobExecution);
					}*/
					List<Throwable> failureExceptions = getFailureExceptions(jobExecution);
					printFailureExceptions(failureExceptions);
					List<String> failureExitDescriptions = getFailureExitDescriptions(jobExecution);
					printFailureDescriptions(failureExitDescriptions);
				}
			}
		}
		
		return failedJobExecutions;
	}
	
	private List<Throwable> getFailureExceptions(JobExecution jobExecution) {
		List<Throwable> failureExceptions = new ArrayList<>();
		
		if (!jobExecution.getExitStatus().equals(ExitStatus.FAILED)) {
			return failureExceptions;
		}
		
		List<Throwable> jobFailureExceptions = jobExecution.getFailureExceptions();
		failureExceptions.addAll(jobFailureExceptions);
		
		for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
			List<Throwable> stepFailureExceptions = stepExecution.getJobExecution().getFailureExceptions();
			failureExceptions.addAll(stepFailureExceptions);
		}
		
		return failureExceptions;
	}
	
	private List<String> getFailureExitDescriptions(JobExecution jobExecution) {
		List<String> exitDescriptions = new ArrayList<>();
		
		if (!jobExecution.getExitStatus().equals(ExitStatus.FAILED)) {
			return exitDescriptions;
		}
		
		ExitStatus jobExitStatus = jobExecution.getExitStatus();
		
		if (jobExitStatus.getExitDescription().isEmpty()) {
			exitDescriptions.add(jobExitStatus.getExitDescription());
		}
		
		for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
			ExitStatus stepExitStatus = stepExecution.getExitStatus();
			if (stepExitStatus.equals(ExitStatus.FAILED) && !"".equals(stepExitStatus.getExitDescription())) {
				exitDescriptions.add(stepExitStatus.getExitDescription());
			}
		}
		
		return exitDescriptions;
	}
	
	private void printFailureExceptions(List<Throwable> failureExceptions) {
		System.out.println(failureExceptions);
	}
	
	private void printFailureDescriptions(List<String> failureDescriptions) {
		System.out.println(failureDescriptions);
	}
}