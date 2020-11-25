package com.example.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "products")
public class Products {
	private String importId;
	private List<Product> products;
	
	public String getImportId() {
		return importId;
	}

	@XmlAttribute(name = "import-id")
	public void setImportId(String importId) {
		this.importId = importId;
	}

	public List<Product> getProducts() {
		return products;
	}

	@XmlElement(name = "product")
	public void setProducts(List<Product> products) {
		this.products = products;
	}
}