package com.example.notifier;

import org.springframework.batch.core.JobExecution;

public interface BatchMonitoringNotifier {
	void notify(JobExecution jobExecution);
}