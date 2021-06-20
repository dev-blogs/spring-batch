package com.example.notifier;

import org.springframework.batch.core.JobExecution;

public class BatchMonitoringNotifierImpl implements BatchMonitoringNotifier {
	@Override
	public void notify(JobExecution jobExecution) {
		System.out.println("Exit status: " + jobExecution.getExitStatus().getExitCode());
	}
}