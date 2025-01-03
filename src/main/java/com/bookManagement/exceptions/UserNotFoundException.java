package com.bookManagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	String message;
	
	public UserNotFoundException(String message) {
		super();
		this.message = message;
	}
	
	public UserNotFoundException() {
		System.out.println("UserNotFoundException: User not found");
	}
	
	
}
