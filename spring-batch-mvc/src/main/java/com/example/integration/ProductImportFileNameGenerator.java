package com.example.integration;

import javax.xml.bind.JAXBException;
import org.springframework.integration.Message;
import org.springframework.integration.file.FileNameGenerator;
import org.springframework.oxm.XmlMappingException;
import org.springframework.util.Assert;
import com.example.utils.ProductImportUtils;

public class ProductImportFileNameGenerator implements FileNameGenerator {
	@Override
	public String generateFileName(Message<?> message) {
		Assert.notNull(message.getPayload());
		Assert.isInstanceOf(String.class, message.getPayload());
		
		String payload = (String) message.getPayload();
		try {
			return ProductImportUtils.extractImportId(payload);
		} catch (JAXBException | XmlMappingException e) {
			throw new RuntimeException(e);
		}
	}
}