package com.example.batch.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import com.example.notifier.SimpleMessageApplicationEvent;

@Component
public class MessageEventListener implements ApplicationListener<SimpleMessageApplicationEvent> {
	@Override
	public void onApplicationEvent(SimpleMessageApplicationEvent event) {
		SimpleMessageApplicationEvent msgEvt = (SimpleMessageApplicationEvent) event;
		System.out.println("Received: " + msgEvt);
	}
}