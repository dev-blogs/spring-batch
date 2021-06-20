package com.example.batch.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.notifier.BatchMonitoringNotifier;

@Component("monitoringJobListener")
public class MonitoringExecutionListener {
	@Autowired
	private BatchMonitoringNotifier batchMonitoringNotifier;

	@BeforeJob
	public void executeBeforeJob(JobExecution jobExecution) {
	}

	@AfterJob
	public void executionAfterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.FAILED) {
			batchMonitoringNotifier.notify(jobExecution);
		}
	}
}