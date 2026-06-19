package com.example.toshokan.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordUpdateRequest {
	private String token;
	private String newPassword;

}
