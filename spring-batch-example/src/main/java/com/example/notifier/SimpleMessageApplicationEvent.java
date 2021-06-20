package com.example.notifier;

import org.springframework.context.ApplicationEvent;

public class SimpleMessageApplicationEvent extends ApplicationEvent {
	private String message;

	public SimpleMessageApplicationEvent(Object source, String message) {
		super(source);
		this.message = message;
	}
	
	public String toString() {
		return "message=["+message+"], " + super.toString();
	}
}