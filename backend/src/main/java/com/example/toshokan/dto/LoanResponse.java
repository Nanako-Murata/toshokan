package com.example.toshokan.dto;

import java.time.LocalDate;

public record LoanResponse(	Integer id,
	    String title,
	    String author,
	    LocalDate loanDate
) {

}
