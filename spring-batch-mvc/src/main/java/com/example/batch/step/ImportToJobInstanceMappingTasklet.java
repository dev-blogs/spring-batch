package com.example.batch.step;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import com.example.repository.jdbc.ProductImportRepository;

public class ImportToJobInstanceMappingTasklet implements Tasklet, InitializingBean {
	private String productImoprtId;
	private ProductImportRepository productImportRepository;
	
	public void setProductImoprtId(String productImoprtId) {
		this.productImoprtId = productImoprtId;
	}

	public void setProductImportRepository(ProductImportRepository productImportRepository) {
		this.productImportRepository = productImportRepository;
	}

	@Override
	public void afterPropertiesSet() {
		Assert.notNull(productImoprtId);
	}
	
	public RepeatStatus execute(StepContribution sc, ChunkContext cc) throws Exception {
		Long jobInstanceId = cc.getStepContext().getStepExecution().getJobExecution().getJobInstance().getId();
		productImportRepository.mapImportToJobInstance(productImoprtId, jobInstanceId);
		return RepeatStatus.FINISHED;
	}
}