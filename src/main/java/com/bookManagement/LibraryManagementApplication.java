package com.bookManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
	    "com.bookManagement.entity",
	    "com.bookManagement.controllers",
	    "com.bookManagement.DAO",
	    "com.bookManagement.exceptions",
	    "com.bookManagement.service",
	    "com.bookManagement"
	})	
@EntityScan("com.bookManagement.entity")
	@SpringBootApplication
	public class LibraryManagementApplication {
	    public static void main(String[] args) {
	        SpringApplication.run(LibraryManagementApplication.class, args);
	    }
	}


