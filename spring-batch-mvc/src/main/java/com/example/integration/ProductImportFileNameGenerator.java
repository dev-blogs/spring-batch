package com.example.integration;

import org.springframework.integration.Message;
import org.springframework.integration.file.FileNameGenerator;
import org.springframework.util.Assert;

public class ProductImportFileNameGenerator implements FileNameGenerator {
	@Override
	public String generateFileName(Message<?> message) {
		Assert.notNull(message.getPayload());
		Assert.isInstanceOf(String.class, message.getPayload());
		
		String payload = (String) message.getPayload();
		return ProductImportUtils.extractImportId(payload);
	}
}