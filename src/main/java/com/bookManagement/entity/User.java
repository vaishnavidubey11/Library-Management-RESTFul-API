package com.bookManagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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
	
	@ManyToOne
	@JoinColumn(name = "owner_id", nullable = false)
	private Owner owner;

	
}
