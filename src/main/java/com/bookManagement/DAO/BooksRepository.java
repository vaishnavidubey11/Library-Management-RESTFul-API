package com.bookManagement.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookManagement.entity.Books;

@Repository
public interface BooksRepository extends JpaRepository<Books, Integer>{

	List<Books> findByTitleContainingIgnoreCase(String title);
	List<Books> findByAuthorContainingIgnoreCase(String Author);
	Books findByISBN(String isbn);
	List<Books> findByGenreContainingIgnoreCase(String genre);
	
	  @Query(value = "SELECT * FROM books WHERE isbn = :isbn", nativeQuery = true)
	    Optional<Books> getByISBN(@Param("isbn") String isbn);
	

}
