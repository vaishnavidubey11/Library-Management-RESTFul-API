package com.bookManagement.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookManagement.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByEmailId(String email);

}
