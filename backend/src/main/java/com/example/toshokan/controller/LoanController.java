package com.example.toshokan.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@GetMapping("/loans/current")
	public Page<Loan> currentLoans(@RequestParam Integer userId, Pageable pageable){
		return loanService.getCurrentLoans(userId, pageable);
	
	}
	
	@PostMapping("/loans/{id}/return")
	public void return (@PathVariable Integer id) {
		loanService.return(id);
	}


}
