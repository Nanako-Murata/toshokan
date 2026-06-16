package com.example.toshokan.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.toshokan.dto.LoginRequest;
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

		User user = userRepository.findByName(req.getName())
		        .orElseThrow(() -> new RuntimeException("user not found"));

	    if (user == null || !passwordEncoder.matches(req.getPassword(), user.getPassword())) {
	        throw new RuntimeException("login failed");
	    }

	    String token = JwtUtil.generateToken(user.getName());

	    return Map.of("token", token);
	}

		
//		SecurityContext context = SecurityContextHolder.createEmptyContext();
//		context.setAuthentication(auth);
//		SecurityContextHolder.setContext(context);
//
//		HttpSession session = httpRequest.getSession(true);
//
//		session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
//
//		// 🔥これが今回の本命
//		session.setAttribute("LOGIN_USER", user);
//
//		return user;

//spring security の標準ログアウトに切り替え
//	@PostMapping("/logout")
////	public ResponseEntity<?> logout(HttpSession session) {
////		session.invalidate();
////		return ResponseEntity.ok("logout success");
////	}
/// 
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody LoginRequest request) {
		try {
			userService.signup(request.getName(), request.getPassword());
			return ResponseEntity.ok("登録成功");

		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(409).body(e.getMessage());
		}

//	@PostMapping("/signup")
//	public void signup(@RequestBody LoginRequest request) {
//		userService.signup(request.getName(), request.getPassword());
//	}
//	@PostMapping("/api/login")
//	public ResponseEntity<?> login(@RequestBody LoginRequest req, HttpSession session) {
//
//		// 認証チェック（例）
//		User user = userService.login(req.getName(), req.getPassword());
//
//		session.setAttribute("LOGIN_USER", user);
//
//		return ResponseEntity.ok("ok");
//	}
//

	}
}
