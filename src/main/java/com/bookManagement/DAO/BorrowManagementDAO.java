package com.bookManagement.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookManagement.entity.Books;
import com.bookManagement.entity.BorrowBooksEntity;
import com.bookManagement.entity.User;
import com.bookManagement.enums.BookStatus;

@Repository
public interface BorrowManagementDAO extends JpaRepository<BorrowBooksEntity, Integer>{

	BorrowBooksEntity findByUserInfoAndBookInfoAndBookStatus(User userInfo, Books bookInfo, BookStatus issued);

}
