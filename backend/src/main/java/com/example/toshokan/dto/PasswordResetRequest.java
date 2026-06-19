package com.example.toshokan.dto;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class PasswordResetRequest {
	private String name;
	private String email;

}
