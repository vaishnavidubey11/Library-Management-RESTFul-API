package com.bookManagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class OwnerNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	String message;
	
	public OwnerNotFoundException(String message) {
		super();
		this.message = message;
	}
	
	public OwnerNotFoundException() {
		System.out.println("OwnerNotFoundException: Owner not found");
	}
	
	
}
