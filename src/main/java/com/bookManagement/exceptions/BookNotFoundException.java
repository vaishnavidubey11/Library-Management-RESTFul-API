package com.bookManagement.exceptions;

public class BookNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	String message;

	public BookNotFoundException(String message) {
		super();
		this.message = message;
	}
	
}
