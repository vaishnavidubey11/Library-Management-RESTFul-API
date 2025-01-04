package com.bookManagement.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookManagement.DAO.BooksRepository;
import com.bookManagement.DAO.BorrowManagementDAO;
import com.bookManagement.DAO.OwnerRepository;
import com.bookManagement.DAO.UserRepository;
import com.bookManagement.entity.Books;
import com.bookManagement.entity.BorrowBooksEntity;
import com.bookManagement.entity.Owner;
import com.bookManagement.entity.User;
import com.bookManagement.enums.BookStatus;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
public class BorrowManagementService {

    @Autowired
    private BorrowManagementDAO borrowManagementDAO;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BooksRepository booksRepository;
    
    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private BooksService booksService;

    private Books books;

    public boolean isUserRegistered() {
        String email = (String) httpSession.getAttribute("email");
        return (email != null);
    }
    
    private Owner getOwner() {
    	int ownerId = (Integer) booksService.httpSession.getAttribute("OwnerId");
    	
        return ownerRepository.findById(ownerId) ;
    }

    public BorrowBooksEntity borrowBook(String isbn) {
        if (isUserRegistered()) {
            User userInfo = userRepository.findByEmailId((String) httpSession.getAttribute("email"));

            Books bookInfo = booksRepository.findByISBNAndOwner(isbn, getOwner());

            if (bookInfo != null && bookInfo.getQuantity() > 0) {

            	BorrowBooksEntity borrowBooksEntity = new BorrowBooksEntity();
                borrowBooksEntity.setUserInfo(userInfo);
                borrowBooksEntity.setBookInfo(bookInfo);
                borrowBooksEntity.setIssueDate(LocalDateTime.now());
                borrowBooksEntity.setReturnDate(LocalDateTime.now().plusDays(7)); 
                borrowBooksEntity.setBookStatus(BookStatus.ISSUED);
                borrowBooksEntity.setOwner(getOwner());

                bookInfo.setQuantity(bookInfo.getQuantity() - 1);

                borrowManagementDAO.save(borrowBooksEntity);
                booksRepository.save(bookInfo); 
                return borrowBooksEntity;
            }
        }
        return null;
    }
    

    public BorrowBooksEntity returnBook(String isbn) {
        if (isUserRegistered()) {
            // Fetch the book and user info
        	
            User userInfo = userRepository.findByEmailId((String) httpSession.getAttribute("email"));
            Books bookInfo = booksRepository.findByISBNAndOwner(isbn, getOwner());
            BorrowBooksEntity borrowBooksEntity = borrowManagementDAO.findByUserInfoAndBookInfoAndBookStatus(userInfo, bookInfo, BookStatus.ISSUED);

            if (bookInfo != null) {
              

                if (borrowBooksEntity != null) {
                    borrowBooksEntity.setBookStatus(BookStatus.RETURNED);
                    borrowBooksEntity.setReturnedOn(LocalDateTime.now()); 
                    borrowBooksEntity.setOwner(getOwner());

                    bookInfo.setQuantity(bookInfo.getQuantity() + 1);
                    
                    borrowManagementDAO.save(borrowBooksEntity);
                    booksRepository.save(bookInfo); 
                    return borrowBooksEntity;
                }
            }
        }
        return null;
    }

    public boolean isBookAvailable(String isbn) {
        Books bookInfo = booksRepository.findByISBNAndOwner(isbn, getOwner());
        
        return bookInfo != null && bookInfo.getQuantity() > 0;
    }
}
