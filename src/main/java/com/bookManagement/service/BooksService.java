package com.bookManagement.service;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookManagement.DAO.BooksRepository;
import com.bookManagement.DAO.OwnerRepository;
import com.bookManagement.entity.Books;
import com.bookManagement.entity.Owner;
import com.bookManagement.exceptions.BookAlreadyExistsException;
import com.bookManagement.exceptions.BookNotFoundException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
public class BooksService {
	
	
	
	
	@Autowired
	private BooksRepository booksRepository;
	
	@Autowired
	private OwnerRepository ownerRepository;
	
	
	
	public boolean addBooks(Books book, int ownerId) throws BookAlreadyExistsException {
	    Owner owner = ownerRepository.findById(ownerId)
	        .orElseThrow(() -> new IllegalArgumentException("Owner not found with id " + ownerId));
	    book.setOwner(owner);

	        	if(book.getPrice() <0) {
	        		return false;
	        	}
	            booksRepository.save(book);
	            return true;
	       
	    }
	
	public boolean removeBooks(String isbn) throws BookNotFoundException {

		Optional<Books> optionalBook = booksRepository.getByISBN(isbn);
	    
	    if (optionalBook.isEmpty()) {
	        throw new BookNotFoundException("The book with ISBN " + isbn + " does not exist.");
	    }
	    
	    Books book = optionalBook.get();
	    booksRepository.deleteById(book.getId());
	    return true;
	}

	public Books updateBooks( String isbn , Books updatedBook) throws BookNotFoundException {
		
		Books book = (Books) booksRepository.findByISBN(isbn);
		Books existingBook = booksRepository.findById(book.getId()).orElseThrow(()-> new BookNotFoundException("Book not found at ISBN "+ isbn));
			
			existingBook.setTitle(updatedBook.getTitle());
			existingBook.setAuthor(updatedBook.getAuthor());
			existingBook.setGenre(updatedBook.getGenre());
			existingBook.setISBN(updatedBook.getISBN());
			existingBook.setQuantity(updatedBook.getQuantity());
			
			return booksRepository.save(existingBook);
	}
	
	public boolean deleteAllBooks() {
		try {
			booksRepository.deleteAll();
			return true;
		}catch (Exception e) {
			return false;
		} 
	}
	public Map<String, List<Books>> addAllBooks(List<Books> bookList, int ownerId) {
	    Owner owner = ownerRepository.findById(ownerId)
	        .orElseThrow(() -> new IllegalArgumentException("Owner not found with id " + ownerId));
	    bookList.forEach(book -> book.setOwner(owner)); 

	    List<Books> addedBooks = new ArrayList<>();
	    List<Books> skippedBooks = new ArrayList<>();

	    for (Books book : bookList) {
	        if (booksRepository.findByISBN(book.getISBN()) != null) {
	            addedBooks.add(book); 
	        } else {
	            skippedBooks.add(book); 
	        }
	    }

	    if (!addedBooks.isEmpty()) {
	        booksRepository.saveAll(addedBooks);
	    }

	    Map<String, List<Books>> result = new HashMap<>();
	    result.put("added", addedBooks);
	    result.put("skipped", skippedBooks);

	    return result;
	}

}
