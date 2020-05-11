package com.example.batch;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;

public class CustomIncrementer implements JobParametersIncrementer {
	@Override
	public JobParameters getNext(JobParameters parameters) {
		return new JobParametersBuilder()
				.addString("inputResource", "classpath:/input/products.zip")
				.addString("targetDirectory", "./target/importproductsbatch/")
				.addString("targetFile", "products.txt")
				.addLong("timestamp", System.currentTimeMillis())
				.toJobParameters();
	}
}