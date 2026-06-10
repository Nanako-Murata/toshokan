package com.example.toshokan.service;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.toshokan.entity.Book;
import com.example.toshokan.entity.Loan;
import com.example.toshokan.entity.User;
import com.example.toshokan.repository.BookRepository;
import com.example.toshokan.repository.LoanRepository;
import com.example.toshokan.repository.UserRepository;

@Service
public class LoanService {
	private LoanRepository loanRepository;

	private BookRepository bookRepository;

	private UserRepository userRepository;

	public LoanService(LoanRepository loanRepository, BookRepository bookRepository, UserRepository userRepository) {
		this.loanRepository = loanRepository;
		this.bookRepository = bookRepository;
		this.userRepository = userRepository;
	}

	public boolean borrow(Integer userId, Integer bookId) {
		Book book = bookRepository.findById(bookId).orElseThrow();
		User user = userRepository.findById(userId).orElseThrow();
		
		if(book.getStatus() == 1) {
			return false;
		}
		
		Loan loan = new Loan();
		loan.setUser(user);
		loan.setBook(book);
		loan.setLoanDate(LocalDate.now());
		loan.setReturnDate(null);
		loanRepository.save(loan);
		book.setStatus(1);
		bookRepository.save(book);
		return true;
	}
	
	public Page<Loan> getCurrentLoans(Integer userId, Pageable pageable){
		User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found."));
		return loanRepository.findByUserAndReturnDateIsNull(user, pageable);
	}
	
	public Page<Loan> getHistory(Integer userId, Pageable pageable){
		User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found."));
		return loanRepository.findByUserAndReturnDateIsNotNull(user, pageable);
	}
	
	

}
