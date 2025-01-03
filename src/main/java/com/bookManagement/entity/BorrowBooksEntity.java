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

    @SuppressWarnings("unused")
	private LocalDateTime returnDate;
    @SuppressWarnings("unused")
	private LocalDateTime issueDate;

    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;

	

}
