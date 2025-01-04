package com.bookManagement.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getBooksByTitle(@PathVariable String title) {
      	Map<String, String> map = new HashMap<>();
        try {
            List<Books> getBooks = searchBooksService.getBookByTitle(title);
            if (getBooks.isEmpty()) {
                return new ResponseEntity<>("No books found for the title: " + title, HttpStatus.NOT_FOUND);
            }else {
            	for (Books books : getBooks) {
					map.put("title", books.getTitle());
					map.put("author", books.getAuthor());
					map.put("genre", books.getGenre());
					map.put("price", String.valueOf(books.getPrice()));
					map.put("ownerBy", books.getOwner().getName());
					map.put("ISBN", books.getISBN());
					
				}
            }
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<?> getBooksByAuthor(@PathVariable String author) {
      	Map<String, String> map = new HashMap<>();
        try {
            List<Books> getBooks = searchBooksService.getBookByAuthor(author);
            if (getBooks.isEmpty()) {
                return new ResponseEntity<>("No books found for the author: " + author, HttpStatus.NOT_FOUND);
            }else {
            	for (Books books : getBooks) {
					map.put("title", books.getTitle());
					map.put("author", books.getAuthor());
					map.put("genre", books.getGenre());
					map.put("price", String.valueOf(books.getPrice()));
					map.put("ownerBy", books.getOwner().getName());
					map.put("ISBN", books.getISBN());
				}
            }
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<?> getBooksByIsbn(@PathVariable String isbn) {
    	Map<String, String> map = new HashMap<>();
    	
        try {
            Books getBooks = searchBooksService.getBookByIsbn(isbn);
            if (getBooks == null) {
                return new ResponseEntity<>("No book found with ISBN: " + isbn, HttpStatus.NOT_FOUND);
            }else {
            	
					map.put("title", getBooks.getTitle());
					map.put("author", getBooks.getAuthor());
					map.put("genre", getBooks.getGenre());
					map.put("price", String.valueOf(getBooks.getPrice()));
					map.put("ownerBy", getBooks.getOwner().getName());
					map.put("ISBN", getBooks.getISBN());
				
            }
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<?> getBooksByGenre(@PathVariable String genre) {
    	
    	Map<String, String> map = new HashMap<>();
        try {
            List<Books> getBooks = searchBooksService.getBookByGenre(genre);
            if (getBooks.isEmpty()) {
                return new ResponseEntity<>("No books found for the genre: " + genre, HttpStatus.NOT_FOUND);
            }else {
            	for (Books books : getBooks) {
					map.put("title", books.getTitle());
					map.put("author", books.getAuthor());
					map.put("genre", books.getGenre());
					map.put("price", String.valueOf(books.getPrice()));
					map.put("ownerBy", books.getOwner().getName());
					map.put("ISBN", books.getISBN());
					
				}
            }
           return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllBooks() {
    	
    	Map<String, String> map = new HashMap<>();
        try {
            List<Books> getBooks = searchBooksService.getAllBooks();
            if (getBooks.isEmpty()) {
                return new ResponseEntity<>("No books available", HttpStatus.NOT_FOUND);
            }else {
            	for (Books books : getBooks) {
					map.put("title", books.getTitle());
					map.put("author", books.getAuthor());
					map.put("genre", books.getGenre());
					map.put("price", String.valueOf(books.getPrice()));
					map.put("ownerBy", books.getOwner().getName());
					map.put("ISBN", books.getISBN());
					
				}
            }
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
