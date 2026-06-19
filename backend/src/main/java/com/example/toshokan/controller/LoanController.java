package com.example.toshokan.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.toshokan.dto.LoanResponse;
import com.example.toshokan.service.LoanService;

@RestController
public class LoanController {

	private final LoanService loanService;

	public LoanController(LoanService loanService) {
		this.loanService = loanService;
	}

	@GetMapping("/loans/current")
	public Page<LoanResponse> currentLoans(Pageable pageable) {
		return loanService.getCurrentLoans(pageable);
	}

	@GetMapping("/loans/history")
	public Page<LoanResponse> history(Pageable pageable) {
		return loanService.getHistory(pageable);
	}

	// 貸し出し処理
	@PostMapping("/book/{bookId}/borrow")
	public ResponseEntity<?> borrowBook(@PathVariable Integer bookId) {
		try {
			boolean result = loanService.borrowBook(bookId);
			if (!result) {
				return ResponseEntity.badRequest().body("すでに貸し出し中です");
			}
			return ResponseEntity.ok("貸し出し成功");
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// 返却処理
	@PostMapping("/loan/{loanId}/return")
	public ResponseEntity<?> returnBook(@PathVariable Integer loanId) {
		loanService.returnBook(loanId);
		return ResponseEntity.ok("返却成功");
	}

}
