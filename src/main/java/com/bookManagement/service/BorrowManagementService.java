package com.bookManagement.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookManagement.DAO.BooksRepository;
import com.bookManagement.DAO.BorrowManagementDAO;
import com.bookManagement.DAO.UserRepository;
import com.bookManagement.entity.Books;
import com.bookManagement.entity.BorrowBooksEntity;
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
	private  BorrowBooksEntity borrowBooksEntity;

	@Autowired
	private Books books;
	
	
	 public boolean isUserRegistered() {
		 
	        String email = (String) httpSession.getAttribute("email");
	        return (email != null);
	    }

	
	public boolean borrowBook(String isbn) {
		if(isUserRegistered()) {
			LocalDateTime date = LocalDateTime.now();
			User userInfo = userRepository.findByEmailId((String)httpSession.getAttribute("email"));
			Books bookInfo = booksRepository.findByISBN(isbn);
			if(isBookAvailable(isbn)) {
				borrowBooksEntity.setUserInfo(userInfo);
				borrowBooksEntity.setBookInfo(bookInfo);
				borrowBooksEntity.setIssueDate(date);
				borrowBooksEntity.setReturnDate(date.plusDays(7));
				borrowBooksEntity.setBookStatus(BookStatus.ISSUED);
				books.setQuantity(books.getQuantity()-1);
				return borrowManagementDAO.save(borrowBooksEntity)!= null;
			}
		}
			return false;
	}
	
	public boolean returnBook() {
		
		if(borrowBooksEntity.getBookStatus() == BookStatus.ISSUED && borrowBooksEntity.getUserInfo().getEmailId() == (String) httpSession.getAttribute("email")) {
			borrowBooksEntity.setBookStatus(BookStatus.RETURNED);
			books.setQuantity(books.getQuantity()+1);
			return true;
		}
		
		return false;
	}
	
	public boolean isBookAvailable(String isbn) {
		Books bookInfo = booksRepository.findByISBN(isbn);
		if(bookInfo.getQuantity()<=0) {
			return false;
		}
		return true;
	}
	
	
	
}
