package com.bookManagement.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Owner {
    
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	private String name;
	@Column(unique = true, nullable = false)	
	private String contactNo;
	@Column(unique = true, nullable = false)	
	private String emailId;
	@Column(unique = true, nullable = false)	
	private String username;
	@Column(unique = false, nullable = false)	
	private String password;

    @OneToMany(mappedBy = "owner")
    private List<Books> books;
    
    @OneToMany(mappedBy = "owner")
    private List<User> users;
    
    @OneToMany(mappedBy = "owner") 
    private List<BorrowBooksEntity> borrowedBooks;
    
}
