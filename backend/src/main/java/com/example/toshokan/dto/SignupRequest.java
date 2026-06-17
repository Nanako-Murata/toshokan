package com.example.toshokan.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
	private String name;
	private String password;
	private String email;

}
