package com.example.toshokan.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.toshokan.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{
	public Page<Book> findAll(Pageable pageable);
	
	@Query
	("""
			select b from book b
			where b.title like %:keyword%
			or b.author like %:keyword%
			or b.detail like  %:keyword%
			""")
	public Page<Book> search(@Param("keyword") String keyword, Pageable pageable); 

}
