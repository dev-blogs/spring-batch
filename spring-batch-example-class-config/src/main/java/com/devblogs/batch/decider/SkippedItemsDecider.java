package com.devblogs.batch.decider;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

public class SkippedItemsDecider implements JobExecutionDecider {
	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
		if (!ExitStatus.FAILED.equals(stepExecution.getExitStatus())
				&& stepExecution.getSkipCount() > 0) {
			return new FlowExecutionStatus("COMPLETED WITH SKIPS");
		} else {
			return new FlowExecutionStatus(jobExecution.getExitStatus().toString());
		}
	}
}