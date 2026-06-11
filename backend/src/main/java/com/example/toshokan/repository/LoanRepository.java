package com.example.toshokan.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.toshokan.entity.Loan;
import com.example.toshokan.entity.User;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {
	public Page<Loan> findByUser(User user, Pageable pageablle);

	public Page<Loan> findByUserAndReturnDateIsNull(User user, Pageable pageable);

	public Page<Loan> findByUserAndReturnDateIsNotNull(User user, Pageable pageable);

}
