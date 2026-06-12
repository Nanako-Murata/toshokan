package com.example.toshokan.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.toshokan.dto.LoginRequest;
import com.example.toshokan.entity.User;
import com.example.toshokan.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class AuthController {

	private final UserService userService;

	public AuthController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {

		User user = userService.login(request.getName(), request.getPassword());

		if (user == null) {
			return ResponseEntity.status(401).body("名前、パスワードが違います");
		}

		Authentication auth = new UsernamePasswordAuthenticationToken(user.getName(), null,
				List.of(new SimpleGrantedAuthority("ROLE_USER")));

		SecurityContextHolder.getContext().setAuthentication(auth);
		httpRequest.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
				SecurityContextHolder.getContext());

		return ResponseEntity.ok(user);
		
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
	}

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
