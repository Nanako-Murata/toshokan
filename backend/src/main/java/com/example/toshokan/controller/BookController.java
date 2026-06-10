package com.example.toshokan.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.toshokan.entity.Book;
import com.example.toshokan.repository.BookRepository;
import com.example.toshokan.service.BookService;

@RestController
@CrossOrigin(origins = "*")
public class BookController {
	private final BookService bookService;
	private final BookRepository bookRepository;

	public BookController(BookService bookService, BookRepository bookRepository) {
		this.bookService = bookService;
		this.bookRepository = bookRepository;
	}

	@GetMapping("/books")
	public Page<Book> getBooks(Pageable pageable) {
		return bookRepository.findAll(pageable);
	}

	@GetMapping("/book/{id}")
	public Book getBook(@PathVariable Integer id) {
		return bookRepository.findById(id).orElseThrow();
	}

	// register a book
	@PostMapping("/books")
	public Book create(@RequestBody Book book) {
		book.setStatus(0);
		return bookRepository.save(book);
	}
	
	@GetMapping("/books/search")
	public Page<Book> search(@RequestParam String keyword, Pageable pageable){
		return bookRepository.search(keyword, pageable);
	}

}
