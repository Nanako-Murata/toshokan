package com.example.toshokan.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.toshokan.entity.User;
import com.example.toshokan.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final EmailService emailService;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.emailService = emailService;
	}

//	public void signup(String name, String password) {
//		User user = new User();
//		user.setName(name);
//		user.setPassword(passwordEncoder.encode(password));
//		userRepository.save(user);
//	}
//
//	public User login(String name, String password) {
//		User user = userRepository.findByName(name).orElseThrow(() -> new RuntimeException("ユーザーが見つかりません"));
//		if (!passwordEncoder.matches(password, user.getPassword())) {
//			throw new RuntimeException("Invalid password");
//
//		}
//		return user;
//	}
	public User login(String name, String password) {

		User user = userRepository.findByName(name).orElseThrow(() -> new RuntimeException("failed"));

		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new RuntimeException("failed");
		}

		return user;
	}

	public void signup(String name, String password, String email) {
	    if (userRepository.existsByName(name)) {
	        throw new IllegalArgumentException("この名前は登録済みです");
	    }

	    User user = new User();
	    user.setName(name);
	    user.setPassword(passwordEncoder.encode(password));
	    userRepository.save(user);
	}

//	public void verify(String token) {
//		User user = userRepository.findByVerifyToken(token)
//				.orElseThrow(() -> new IllegalArgumentException("無効なトークンです"));
//		if (user.isVerified()) {
//			throw new IllegalArgumentException("すでに認証済みです");
//
//		}
//		user.setVerified(true);
//		user.setVerifyToken(null);
//
//		userRepository.save(user);
//	}

//	// パスワード変更用method
//	@Transactional
//	public void requestPasswordReset(String name, String email) {
//		Optional<User> userOpt = userRepository.findByNameAndEmail(name, email);
//		if (userOpt.isEmpty()) {
//			return;
//		}
//		User user = userOpt.get();
//		String token = UUID.randomUUID().toString();
//		user.setPasswordResetToken(token);
//		String resetUrl = "https://toshokan-frontend.onrender.com/password-update?token=" + token;
//
//		try {
//			emailService.sendPasswordResetNotification(user.getEmail(), user.getName(), resetUrl);
//		} catch (Exception e) {
//			System.err.println("メール送信失敗" + e.getMessage());
//		}
//	}
//
//	/*
//	 * 新しいパスワードへの書き換え処理
//	 * 
//	 * @return
//	 * 
//	 */
//	@Transactional
//	public boolean updatePassword(String token, String newPassword) {
//		Optional<User> userOpt = userRepository.findByPasswordResetToken(token);
//		if (userOpt.isEmpty()) {
//			return false;
//
//		}
//		User user = userOpt.get();
//		user.setPassword(passwordEncoder.encode(newPassword));
//		user.setPasswordResetToken(null);
//		userRepository.save(user);
//		return true;
//	}
}
