package com.bookManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookManagement.service.BorrowManagementService;

@RestController
@RequestMapping("/api")
public class BorrowManagementController {
	
	@Autowired
	private BorrowManagementService borrowManagementService;
	
	@PostMapping("/borrowBook")
	public ResponseEntity<String> borrowBook(@PathVariable String isbn) {
		try {
			boolean borrowBook=	borrowManagementService.borrowBook(isbn);
			if(borrowBook) {
	            return new ResponseEntity<>("Borrowed book successfully", HttpStatus.CREATED);
			}else {
				return new ResponseEntity<>("Unable to borrow book", HttpStatus.NOT_IMPLEMENTED);
			}
		} catch (Exception e) {
			
			return new ResponseEntity<>("An error occured", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	
}
