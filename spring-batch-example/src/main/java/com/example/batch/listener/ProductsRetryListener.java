package com.example.batch.listener;

import org.springframework.batch.retry.RetryCallback;
import org.springframework.batch.retry.RetryContext;
import org.springframework.batch.retry.RetryListener;

public class ProductsRetryListener implements RetryListener {

	public ProductsRetryListener() {
	}

	@Override
	public <T> boolean open(RetryContext context, RetryCallback<T> callback) {
		System.out.println("open");
		return false;
	}

	@Override
	public <T> void close(RetryContext context, RetryCallback<T> callback, Throwable throwable) {
		System.out.println("close");
	}

	@Override
	public <T> void onError(RetryContext context, RetryCallback<T> callback, Throwable throwable) {
		System.out.println("on error");
	}
}