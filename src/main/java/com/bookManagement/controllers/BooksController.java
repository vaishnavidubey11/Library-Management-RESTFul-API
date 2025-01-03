package com.bookManagement.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookManagement.entity.Books;
import com.bookManagement.exceptions.BookAlreadyExistsException;
import com.bookManagement.exceptions.BookNotFoundException;
import com.bookManagement.service.BooksService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/books")
public class BooksController {

    @Autowired
    private BooksService booksService;
    
    @Autowired
    private HttpSession httpSession;
    
    @PostMapping("/addBooks")
    public ResponseEntity<String> addBook(@RequestBody Books book) {
        try {
            if (httpSession != null && httpSession.getAttribute("OwnerId") != null) {
                int ownerId = (int) httpSession.getAttribute("OwnerId");
                booksService.addBooks(book, ownerId);
                
                return new ResponseEntity<>("The book is added successfully", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Unauthorized. Please register as an owner.", HttpStatus.BAD_REQUEST);
            }
        }catch (BookAlreadyExistsException e) {
        	return new ResponseEntity<>("This book exists in the system" , HttpStatus.CONFLICT);
		}
        catch (Exception e) {
            return new ResponseEntity<>("An error occurred, please try again", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/update/{isbn}")
    public ResponseEntity<String> updateBook(@PathVariable String isbn, @RequestBody Books book) throws BookNotFoundException {
        try {
        	
        	if(httpSession != null && httpSession.getAttribute("OwnerId")!= null) {
            booksService.updateBooks(isbn, book);
            return new ResponseEntity<>("Book updated successfully", HttpStatus.OK);
        }else {
        	 return new ResponseEntity<>("Unauthorized. Please register as an owner.", HttpStatus.OK);
		}
        
        } catch (BookNotFoundException e) {
            return new ResponseEntity<>("Unable to find book at that ISBN: "+ isbn, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occured, please try again", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{isbn}")
    public ResponseEntity<String> removeBooks(@PathVariable String isbn) {
        try {
            if (httpSession != null && httpSession.getAttribute("OwnerId") != null) {
                booksService.removeBooks(isbn);
                return new ResponseEntity<>("Book removed successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Unauthorized. Please register as an owner.", HttpStatus.UNAUTHORIZED);
            }
        } catch (BookNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while removing the book.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @DeleteMapping("/deleteAllBooks")
    public ResponseEntity<String> deleteAllBooks() {
        try {
        	if(httpSession != null && httpSession.getAttribute("OwnerId")!= null) {
        		booksService.deleteAllBooks();
        		  return new ResponseEntity<>("All books deleted successfully", HttpStatus.OK);
            }else {
            	 return new ResponseEntity<>("Unauthorized. Please register as an owner.", HttpStatus.OK);
    		}
        } catch (Exception e) {
            return new ResponseEntity<>("An error occured, please try again", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/addAllBooks")
    public ResponseEntity<String> addAllBooks(@RequestBody List<Books> books) {
        try {
            if (httpSession != null && httpSession.getAttribute("OwnerId") != null) {
                int ownerId = (int) httpSession.getAttribute("OwnerId");
                Map<String, List<Books>> result = booksService.addAllBooks(books, ownerId);

                List<Books> addedBooks = result.get("added");
                List<Books> skippedBooks = result.get("skipped");

                StringBuilder responseMessage = new StringBuilder();

                if (!addedBooks.isEmpty()) {
                    responseMessage.append("Books added successfully: ")
                        .append(addedBooks.size())
                        .append(". ");
                }

                if (!skippedBooks.isEmpty()) {
                    responseMessage.append("Duplicate books skipped: ")
                        .append(skippedBooks.size())
                        .append(".");
                }

                return new ResponseEntity<>(responseMessage.toString(), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Unauthorized. Please register as an owner.", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
