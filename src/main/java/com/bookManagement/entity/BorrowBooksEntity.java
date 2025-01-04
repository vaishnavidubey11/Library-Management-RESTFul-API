package com.bookManagement.entity;

import java.time.LocalDateTime;

import com.bookManagement.enums.BookStatus;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Table(name = "Borrowedbooks")
@Getter
@Setter
@Entity
public class BorrowBooksEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userInfo;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Books bookInfo;

    private LocalDateTime returnedOn = null; 
	private LocalDateTime returnDate;
	private LocalDateTime issueDate;
	
	@ManyToOne
    @JoinColumn(name = "owner_id", nullable = false) 
    private Owner owner;

    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;
    
    
	

}
