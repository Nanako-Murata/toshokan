package com.example.toshokan.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.toshokan.entity.Loan;
import com.example.toshokan.service.LoanService;
@RestController
@CrossOrigin(origins = "*")
public class LoanController {
	
	private final LoanService loanService;
	
	public LoanController(LoanService loanService) {
		this.loanService=loanService;
	}
	//今借りてる本一覧
	@GetMapping("/loans/current")
	public Page<Loan> currentLoans(Pageable pageable){
		return loanService.getCurrentLoans(pageable);
	
	}
	
	//過去に借りた本一覧
	@GetMapping("/loans/history")
	public Page<Loan> history(Pageable pageable){
		return loanService.getHistory(pageable);
	}
	
	//貸し出し処理
	@PostMapping("/book/{bookId}/borrow")
	public boolean borrowBook(@PathVariable Integer bookId) {
		return loanService.borrowBook(bookId);
	}
	//返却処理
	@PostMapping("/loan/{loanId}/return")
	public void returnBook(@PathVariable Integer bookId) {
		loanService.returnBook(bookId);
	}


}
