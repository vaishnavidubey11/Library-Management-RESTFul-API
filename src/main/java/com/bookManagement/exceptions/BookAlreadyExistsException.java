package com.bookManagement.exceptions;

public class BookAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	String message;
	
	public BookAlreadyExistsException(String message) {
		super();
		this.message = message;
	}

	public BookAlreadyExistsException() {
		super("This Book Exists");
	}
	
	
}
