package com.bookManagement.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookManagement.entity.BorrowBooksEntity;
import com.bookManagement.service.BorrowManagementService;

@RestController
@RequestMapping("/api/borrowServices")
public class BorrowManagementController {
	
	@Autowired
	private BorrowManagementService borrowManagementService;
	
	@PostMapping("/borrowBook/{isbn}")
	public ResponseEntity<?> borrowBook(@PathVariable String isbn) {
		try {
			BorrowBooksEntity borrowBook=	borrowManagementService.borrowBook(isbn);
			if(borrowBook !=null) {
				Map<String, Object> response = new HashMap<>();
				response.put("title", borrowBook.getBookInfo().getTitle());
				response.put("ISBN", borrowBook.getBookInfo().getISBN());
				response.put("author", borrowBook.getBookInfo().getAuthor());
				response.put("returnDate", borrowBook.getReturnDate().toString());
				response.put("issuedOn", borrowBook.getIssueDate().toString());
				response.put("issuedBy", borrowBook.getUserInfo().getName());
				response.put("status", HttpStatus.ACCEPTED);
				
	            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
			}else {
				return new ResponseEntity<>("Unable to proceed with your request", HttpStatus.NOT_IMPLEMENTED);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("An error occured", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/returnBook/{isbn}")
	public ResponseEntity<?>  returnBook(@PathVariable String isbn){
		BorrowBooksEntity isReturned = borrowManagementService.returnBook(isbn);
		try {
			if(isReturned !=null) {
				Map<String, Object> response = new HashMap<>();
				response.put("title", isReturned.getBookInfo().getTitle());
				response.put("ISBN", isReturned.getBookInfo().getISBN());
				response.put("author", isReturned.getBookInfo().getAuthor());
				response.put("returnDate", isReturned.getReturnDate().toString());
				response.put("issuedOn", isReturned.getIssueDate().toString());
				response.put("issuedBy", isReturned.getUserInfo().getName());
				response.put("status", "Book returned successfully");
				
				return new ResponseEntity<>(response,  HttpStatus.ACCEPTED); 
			}
			return new ResponseEntity<>("Returned not returned", HttpStatus.EXPECTATION_FAILED);

		}catch (Exception e) {
			return new ResponseEntity<>("An error occured while returning book", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	
}
