package com.example.toshokan.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.toshokan.entity.Book;
import com.example.toshokan.repository.BookRepository;
import com.example.toshokan.service.BookService;

import jakarta.servlet.http.HttpSession;

@RestController
public class BookController {
	private final BookService bookService;
	private final BookRepository bookRepository;

	public BookController(BookService bookService, BookRepository bookRepository) {
		this.bookService = bookService;
		this.bookRepository = bookRepository;
	}
	

	//本一覧を表示
	@GetMapping("/books")
	public Page<Book> getBooks(Pageable pageable) {
		return bookRepository.findAll(pageable);
	}
	//本の詳細を表示
	@GetMapping("/books/{id}")
	public Book getBook(@PathVariable Integer id) {
		return bookRepository.findById(id).orElseThrow(()->new RuntimeException("not found"));
	}

	// 新しい本を追加
	@PostMapping("/books")
	public Book create(@RequestBody Book book) {
		book.setStatus(0);
		return bookRepository.save(book);
	}
	
	//本一覧を検索
	@GetMapping("/books/search")
	public Page<Book> search(@RequestParam String keyword, Pageable pageable, HttpSession session){
		if(session.getAttribute("LOGIN_USER")==null
				) {
			throw new RuntimeException("NOT LOGIN");
		}
		return bookService.search(keyword, pageable);
	}

}
