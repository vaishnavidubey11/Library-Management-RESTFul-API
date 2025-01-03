package com.bookManagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Books {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
		
    private String ISBN;
    private String title;
    private int quantity =0;
    private String author;
    private String genre;
	@Column(nullable = false)
	private double price;
	
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

}
