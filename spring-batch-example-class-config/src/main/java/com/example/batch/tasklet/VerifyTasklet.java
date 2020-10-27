package com.example.batch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class VerifyTasklet implements Tasklet {
	@Override
	public RepeatStatus execute(StepContribution xtepContribution, ChunkContext chunkContext) throws Exception {
		return RepeatStatus.FINISHED;
	}
}