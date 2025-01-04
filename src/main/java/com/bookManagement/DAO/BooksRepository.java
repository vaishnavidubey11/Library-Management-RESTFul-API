package com.bookManagement.DAO;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookManagement.entity.Books;
import com.bookManagement.entity.Owner;

@Repository
public interface BooksRepository extends JpaRepository<Books, Integer> {
	
	List<Books> findByTitleContainingIgnoreCaseAndOwner(String title, Owner owner);
	
	List<Books> findByGenreContainingIgnoreCaseAndOwner(String genre, Owner owner);
	
	Books findByISBNAndOwner(String iSBN, Owner owner);

	List<Books> findByAuthorContainingIgnoreCaseAndOwner(String author, Owner owner);

	List<Books> findByOwner(Owner owner);
}