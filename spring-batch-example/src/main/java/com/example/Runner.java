package com.example;

import java.util.Collections;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.support.MessageBuilder;
import com.example.batch.JobLaunchRequest;

public class Runner {
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/spring-integration-quick-start.xml");
		
		JobLaunchRequest jobLaunchRequest = new JobLaunchRequest("echoJob", Collections.singletonMap("key1", "value1"));
		Message<JobLaunchRequest> msg = MessageBuilder.withPayload(jobLaunchRequest).build();
		
		MessageChannel messageChannel = (MessageChannel) ctx.getBean("job-request");
		messageChannel.send(msg);
	}
}