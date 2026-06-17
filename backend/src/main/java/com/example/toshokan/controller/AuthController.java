package com.example.toshokan.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.toshokan.dto.LoginRequest;
import com.example.toshokan.dto.SignupRequest;
import com.example.toshokan.entity.User;
import com.example.toshokan.repository.UserRepository;
import com.example.toshokan.security.JwtUtil;
import com.example.toshokan.service.UserService;

@RestController
@RequestMapping("/api")
public class AuthController {

	private final UserService userService;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public AuthController(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/login")
	public Map<String, String> login(@RequestBody LoginRequest req) {

		User user = userRepository.findByName(req.getName()).orElseThrow(() -> new RuntimeException("user not found"));

		if (user == null || !passwordEncoder.matches(req.getPassword(), user.getPassword())) {
			throw new RuntimeException("login failed");
		}

		String token = JwtUtil.generateToken(user.getName());

		return Map.of("token", token);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
		try {
			userService.signup(request.getName(), request.getPassword(), request.getEmail());
			return ResponseEntity.ok("仮登録が完了しました。確認メールを送信しました。");

		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(409).body(e.getMessage());
		}
	}
	
	@GetMapping("/signup/verify")
	public ResponseEntity<?> verify(@RequestParam String token){
		try {
			userService.verify(token);
			return ResponseEntity.ok("登録完了");
			
		}catch(IllegalArgumentException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
}
