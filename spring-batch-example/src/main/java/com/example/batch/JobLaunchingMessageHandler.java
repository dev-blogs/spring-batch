package com.example.batch;

import java.util.Map;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;

public class JobLaunchingMessageHandler {
	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private JobRegistry jobRegistry;
	
	public JobExecution execute(JobLaunchRequest jobRequest)
			throws NoSuchJobException, 
			JobExecutionAlreadyRunningException, 
			JobRestartException, 
			JobInstanceAlreadyCompleteException, 
			JobParametersInvalidException {
		
		Job job = jobRegistry.getJob(jobRequest.getJobName());
		JobParametersBuilder builder = new JobParametersBuilder();
		for (Map.Entry<String, String> entry : jobRequest.getJobParameters().entrySet()) {
			builder.addString(entry.getKey(), entry.getValue());
		}
		return jobLauncher.run(job, builder.toJobParameters());
	}
}