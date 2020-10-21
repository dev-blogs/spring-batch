package com.example.batch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class TrackImportFromJobContextTasklet implements Tasklet {
	private String importId;
	
	public void setImportId(String importId) {
		this.importId = importId;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		System.out.println("importId: " + importId);
		return RepeatStatus.FINISHED;
	}
}