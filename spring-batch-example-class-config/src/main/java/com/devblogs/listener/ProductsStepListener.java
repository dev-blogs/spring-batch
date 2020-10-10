package com.devblogs.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class ProductsStepListener implements StepExecutionListener {

	@Override
	public void beforeStep(StepExecution stepExecution) {
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		if (!ExitStatus.FAILED.equals(stepExecution.getExitStatus()) 
				&& stepExecution.getSkipCount() > 0) {
			return new ExitStatus("COMPLETED WITH SKIPS");
		}
		return stepExecution.getExitStatus();
	}
}