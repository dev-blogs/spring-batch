package com.example.batch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class EchoJobParametersTasklet implements Tasklet {
	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) {
		System.out.println(chunkContext.getStepContext().getJobParameters());
		return RepeatStatus.FINISHED;
	}
}