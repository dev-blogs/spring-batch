package com.example.integration;

import java.util.Map;

public class JobLaunchRequest {
	private String jobName;
	private Map<String, String> jobParameters;
	
	public JobLaunchRequest(String jobName, Map<String, String> jobParameters) {
		super();
		this.jobName = jobName;
		this.jobParameters = jobParameters;
	}
}