package com.example.toshokan.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.toshokan.dto.LoginRequest;
import com.example.toshokan.service.UserService;

@RestController
@CrossOrigin(origins ="*")
public class AuthController {
	private final UserService userService;
	
	public AuthController(UserService userService) {
		this.userService=userService;
	}
	
	@PostMapping("/api/login")
	public boolean login(@RequestBody LoginRequest request) {
		return userService.check(request.getName(), request.getPassword());
	}
	
	@PostMapping("/signup")
	public void signup(@RequestBody LoginRequest request) {
		userService.signup(request.getName(), request.getPassword());
	}

}
