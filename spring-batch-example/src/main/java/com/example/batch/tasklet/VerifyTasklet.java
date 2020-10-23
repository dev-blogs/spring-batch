package com.example.batch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import com.example.batch.data.ImportMetadata;
import com.example.batch.data.ImportMetadataHolder;

public class VerifyTasklet implements Tasklet {
	private ImportMetadataHolder importMetadataHolder;
	
	public void setImportMetadataHolder(ImportMetadataHolder importMetadataHolder) {
		this.importMetadataHolder = importMetadataHolder;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		ImportMetadata importMetadata = new ImportMetadata();
		importMetadata.setImportId("ID1");
		importMetadataHolder.set(importMetadata);
		return RepeatStatus.FINISHED;
	}
}