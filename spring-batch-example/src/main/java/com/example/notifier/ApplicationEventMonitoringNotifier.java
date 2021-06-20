package com.example.notifier;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import org.springframework.batch.core.JobExecution;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationEventMonitoringNotifier implements ApplicationEventPublisherAware, BatchMonitoringNotifier {
	private ApplicationEventPublisher applicationEventPublisher;
	
	@Override
	public void notify(JobExecution jobExecution) {
		String content = createMessageContent(jobExecution);
		applicationEventPublisher.publishEvent(new SimpleMessageApplicationEvent(this, content));
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
	
	private String createMessageContent(JobExecution jobExecution) {
		List<Throwable> exceptions = jobExecution.getFailureExceptions();
		StringBuffer content = new StringBuffer();
		content.append("Job execution #");
		content.append(jobExecution.getId());
		content.append(" of job instance #");
		content.append(jobExecution.getJobInstance().getId());
		content.append(" failed with following exceptions:");
		for (Throwable exception : exceptions) {
			content.append("");
			content.append(formatExceptionMessage(exception));
		}
		return content.toString();
	}
	
	private String formatExceptionMessage(Throwable exception) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		exception.printStackTrace(new PrintStream(baos));
		return baos.toString();
	}
}