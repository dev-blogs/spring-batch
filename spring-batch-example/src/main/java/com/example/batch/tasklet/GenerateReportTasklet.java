package com.example.batch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

public class GenerateReportTasklet implements Tasklet {
	private static final String SQL = "INSERT INTO reports (exit_status_code, description) VALUES (?, ?)";
	
	private SimpleJdbcTemplate simpleJdbcTemplate;

	public GenerateReportTasklet() {
	}
	
	public void setSimpleJdbcTemplate(SimpleJdbcTemplate simpleJdbcTemplate) {
		this.simpleJdbcTemplate = simpleJdbcTemplate;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		simpleJdbcTemplate.update(SQL, "skip items", "skip items");
		return RepeatStatus.FINISHED;
	}
}