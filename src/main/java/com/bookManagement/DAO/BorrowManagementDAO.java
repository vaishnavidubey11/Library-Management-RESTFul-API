package com.bookManagement.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookManagement.entity.BorrowBooksEntity;

@Repository
public interface BorrowManagementDAO extends JpaRepository<BorrowBooksEntity, Integer>{

}
