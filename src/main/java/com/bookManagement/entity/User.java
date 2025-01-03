package com.bookManagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Transactional
public class User {
	
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
	@Column(unique = true, nullable = false)	
	private String password;
	

	
}
