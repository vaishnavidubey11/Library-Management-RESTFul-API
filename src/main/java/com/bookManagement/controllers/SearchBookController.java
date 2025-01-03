package com.bookManagement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookManagement.entity.Books;
import com.bookManagement.service.SearchBooksService;

@RestController
@RequestMapping("/api/search")
public class SearchBookController {
	
	@Autowired
	private SearchBooksService searchBooksService;
	
	@GetMapping("/title/{title}")
	public List<Books> getBooksByTitle(@PathVariable String title){
		return searchBooksService.getBookByTitle(title);
	}
	
	@GetMapping("/author/{author}")	
	public List<Books> getBooksByAuthor(@PathVariable String author){
		return searchBooksService.getBookByAuthor(author);
	}
	
	@GetMapping("/isbn/{isbn}")	
	public Books getBooksByIsbn(@PathVariable String isbn){
		return searchBooksService.getBookByIsbn(isbn);
	}
	
	@GetMapping("/genre/{genre}")	
	public List<Books> getBooksById(@PathVariable String genre){
		return searchBooksService.getBookByGenre(genre);
	}
	
	@GetMapping("/all")	
	public List<Books> getAllBooks(){
		return searchBooksService.getAllBooks();
	}
	
	
	
}
