package com.bookManagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookManagement.DAO.BooksRepository;
import com.bookManagement.DAO.OwnerRepository;
import com.bookManagement.entity.Books;
import com.bookManagement.entity.Owner;

@Service
public class SearchBooksService {

    @Autowired
    private BooksRepository booksRepository;
    
    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private BooksService booksService;

    private Owner getOwner() {
    	int ownerId = (Integer) booksService.httpSession.getAttribute("OwnerId");
    	
        return ownerRepository.findById(ownerId) ;
    }

    public List<Books> getBookByTitle(String title) {
    	return booksRepository.findByTitleContainingIgnoreCaseAndOwner(title, getOwner());
    }

    public List<Books> getBookByAuthor(String author) {
        return booksRepository.findByAuthorContainingIgnoreCaseAndOwner(author, getOwner());
    }

    public Books getBookByIsbn(String isbn) {
        return booksRepository.findByISBNAndOwner(isbn,getOwner());
    }

    public List<Books> getBookByGenre(String genre) {
        return booksRepository.findByGenreContainingIgnoreCaseAndOwner(genre, getOwner());
    }

    public List<Books> getAllBooks() {
        return booksRepository.findByOwner(getOwner());
    }
}
