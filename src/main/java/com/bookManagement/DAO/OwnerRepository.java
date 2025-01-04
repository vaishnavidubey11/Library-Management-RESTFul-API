package com.bookManagement.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookManagement.entity.Owner;


@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {
	
	Owner findByEmailId(String email_id);
	Owner findByContactNo(String contact_no);
	void deleteByEmailId(String email_id);
	Owner  findById(int id);
	
	
}

