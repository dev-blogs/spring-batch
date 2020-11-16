package com.example.exceptions;

public class DublicateKeyException extends Exception {
	private static final long serialVersionUID = 3298697575600313810L;

	public DublicateKeyException() {
		super();
	}
	
	public DublicateKeyException(String message) {
		super(message);
	}
}