package com.example.integration;

public class ProductImport {
	private String importId;
	private String state;
	
	public ProductImport() {
	}
	
	public ProductImport(String importId, String state) {
		super();
		this.importId = importId;
		this.state = state;
	}

	public String getImportId() {
		return importId;
	}

	public void setImportId(String importId) {
		this.importId = importId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}