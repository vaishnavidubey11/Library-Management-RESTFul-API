package com.bookManagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookManagement.DAO.BooksRepository;
import com.bookManagement.entity.Books;

@Service
public class SearchBooksService {
	
	@Autowired
	private BooksRepository booksRepository;
	
	public List<Books> getBookByTitle(String title){
		return booksRepository.findByTitleContainingIgnoreCase(title);
	}
	
	public List<Books> getBookByAuthor(String author){
		return booksRepository.findByAuthorContainingIgnoreCase(author);
	}
	
	public Books getBookByIsbn(String isbn){
		return booksRepository.findByISBN(isbn);
	}
	
	public List<Books> getBookByGenre(String genre){
		return booksRepository.findByGenreContainingIgnoreCase(genre);
	}
	
	public List<Books> getAllBooks(){
		return booksRepository.findAll();
	}
	
}
