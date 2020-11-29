package com.example.utils;

import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import com.example.model.Products;

public class ProductImportUtils {
	public static String extractImportId(String content) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Products.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		StringReader stringReader = new StringReader(content);
		Products customer = (Products) jaxbUnmarshaller.unmarshal(stringReader);
		return customer.getImportId() + ".xml";
	}
}