package com.example.batch.tasklet;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.Resource;

public class DownloadTasklet implements Tasklet {
	private Resource inputResource;
	
	public void setInputResource(Resource inputResource) {
		this.inputResource = inputResource;
	}

	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		if (inputResource.exists()) {
			chunkContext.getStepContext().getStepExecution().setExitStatus(new ExitStatus("FILE EXISTS"));
		} else {
			chunkContext.getStepContext().getStepExecution().setExitStatus(new ExitStatus("NO FILE"));
		}
		return RepeatStatus.FINISHED;
	}
}