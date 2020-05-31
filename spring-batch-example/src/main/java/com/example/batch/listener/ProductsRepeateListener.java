package com.example.batch.listener;

import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.RepeatListener;
import org.springframework.batch.repeat.RepeatStatus;

public class ProductsRepeateListener implements RepeatListener {

	public ProductsRepeateListener() {
	}

	@Override
	public void before(RepeatContext context) {
		System.out.println("before");
	}

	@Override
	public void after(RepeatContext context, RepeatStatus result) {
		System.out.println("after");
	}

	@Override
	public void open(RepeatContext context) {
		System.out.println("open");
	}

	@Override
	public void onError(RepeatContext context, Throwable e) {
		System.out.println("on error");
	}

	@Override
	public void close(RepeatContext context) {
		System.out.println("close");
	}
}