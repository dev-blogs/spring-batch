package com.example.batch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import com.example.batch.data.ImportMetadataHolder;

public class TrackImportWithHolderTasklet implements Tasklet {
	private ImportMetadataHolder importMetadataHolder;
	
	public void setImportMetadataHolder(ImportMetadataHolder importMetadataHolder) {
		this.importMetadataHolder = importMetadataHolder;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		System.out.println("importId: " + importMetadataHolder.get().getImportId());
		return RepeatStatus.FINISHED;
	}
}