package com.bookManagement.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookManagement.DAO.BooksRepository;
import com.bookManagement.DAO.OwnerRepository;
import com.bookManagement.entity.Books;
import com.bookManagement.entity.Owner;
import com.bookManagement.exceptions.BookAlreadyExistsException;
import com.bookManagement.exceptions.BookNotFoundException;
import com.bookManagement.exceptions.OwnerNotFoundException;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
public class BooksService {

    @Autowired
    public HttpSession httpSession;

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    
    private Owner getOwner() {
    	int ownerId = (Integer) httpSession.getAttribute("OwnerId");
    	
        return ownerRepository.findById(ownerId) ;
    }
    

    public boolean addBooks(Books book, int ownerId) throws BookAlreadyExistsException {
    	 Owner existingOwner = ownerRepository.findById(ownerId);
         if(existingOwner == null) {
       	  throw new OwnerNotFoundException();
         }
         
        book.setOwner(existingOwner);;

        if (book.getPrice() >= 0 && (booksRepository.findByISBNAndOwner(book.getISBN(), existingOwner) == null) ) {
        	booksRepository.save(book);
        	return true;
        }
        
        return false;
    }

    public boolean removeBooks(String isbn) throws BookNotFoundException {
        Books books = booksRepository.findByISBNAndOwner(isbn, getOwner());

        if (books == null) {
            throw new BookNotFoundException("The book with ISBN " + isbn + " does not exist.");
        }
        booksRepository.deleteById(books.getId());
        return true;
    }

    public Books updateBooks(String isbn, Books updatedBook) throws BookNotFoundException {
        Books book = booksRepository.findByISBNAndOwner(isbn, getOwner());
        
        if (book == null) {
            throw new BookNotFoundException("Book not found at ISBN " + isbn);
        }

        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        book.setGenre(updatedBook.getGenre());
        book.setISBN(updatedBook.getISBN());
        book.setQuantity(updatedBook.getQuantity());

        return booksRepository.save(book);
    }

    public boolean deleteAllBooks() {
        try {
            booksRepository.deleteAll();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Map<String, List<Books>> addAllBooks(List<Books> bookList, int ownerId) {

    	 Owner existingOwner = ownerRepository.findById(ownerId);
         if(existingOwner == null) {
       	  throw new OwnerNotFoundException();
         }
         
        bookList.forEach(book -> book.setOwner(existingOwner));

        List<Books> addedBooks = new ArrayList<>();
        List<Books> skippedBooks = new ArrayList<>();

        for (Books book : bookList) {
            if (booksRepository.findByISBNAndOwner(book.getISBN(), existingOwner) == null) {
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
