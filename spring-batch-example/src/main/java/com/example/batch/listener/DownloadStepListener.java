package com.example.batch.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class DownloadStepListener implements StepExecutionListener {
	@Override
	public void beforeStep(StepExecution stepExecution) {
		
	}
	
	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		return stepExecution.getExitStatus();
	}
}