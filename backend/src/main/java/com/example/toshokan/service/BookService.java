package com.example.toshokan.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.toshokan.entity.Book;
import com.example.toshokan.repository.BookRepository;

@Service
public class BookService {
	private final BookRepository bookRepository;
	
	public BookService(BookRepository bookRepository) {
		this.bookRepository=bookRepository;
	}
	
	public Page<Book> search(String keyword, Pageable pageable){
		if(keyword ==null || keyword.isBlank()) {
			return bookRepository.findAll(pageable);
		}
		return bookRepository.search(keyword, pageable);
	}


}
