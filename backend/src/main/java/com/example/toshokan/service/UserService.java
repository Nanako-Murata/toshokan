package com.example.toshokan.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.toshokan.entity.User;
import com.example.toshokan.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JavaMailSender mailSender;
	private final EmailService emailService;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JavaMailSender mailSender,
			EmailService emailService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.mailSender = mailSender;
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
		if (userRepository.existsByEmail(email)) {
			throw new IllegalArgumentException("このメールアドレスは登録済みです");
		}
		String token = UUID.randomUUID().toString();

		// verify falseで新規ユーザー作成 仮登録
		User user = new User();
		user.setName(name);
		user.setPassword(passwordEncoder.encode(password));
		user.setEmail(email);
		user.setVerified(false);
		user.setVerifyToken(token);

		userRepository.save(user);

		// 本登録用メール作成
		String verifyUrl = "http://localhost:5173/signup/verify?token=" + token;
//		//デプロイ用
//		String verifyUrl = "https://toshokan-frontend.onrender.com/signup/verify?token=" + token;
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setSubject("図書館アプリ新規会員登録　メールアドレスの確認");
		mail.setText("以下のリンクをクリックして会員登録を完了させてください \n\n" + verifyUrl);

		mailSender.send(mail);

	}

	public void verify(String token) {
		User user = userRepository.findByVerifyToken(token)
				.orElseThrow(() -> new IllegalArgumentException("無効なトークンです"));
		if (user.isVerified()) {
			throw new IllegalArgumentException("すでに認証済みです");

		}
		user.setVerified(true);
		user.setVerifyToken(null);

		userRepository.save(user);
	}

	// パスワード変更用method
	@Transactional
	public void requestPasswordReset(String name, String email) {
		Optional<User> userOpt = userRepository.findByNameAndEmail(name, email);
		if (userOpt.isEmpty()) {
			return;
		}
		User user = userOpt.get();
		String token = UUID.randomUUID().toString();
		user.setPasswordResetToken(token);
		String resetUrl = "http://localhost:5173/password-update?token=" + token;

		try {
			emailService.sendPasswordResetNotification(user.getEmail(), user.getName(), resetUrl);
		} catch (Exception e) {
			System.err.println("メール送信失敗" + e.getMessage());
		}
	}

	/*
	 * 新しいパスワードへの書き換え処理
	 * 
	 * @return
	 * 
	 */
	@Transactional
	public boolean updatePassword(String token, String newPassword) {
		Optional<User> userOpt = userRepository.findByPasswordResetToken(token);
		if (userOpt.isEmpty()) {
			return false;

		}
		User user = userOpt.get();
		user.setPassword(passwordEncoder.encode(newPassword));
		user.setPasswordResetToken(null);
		userRepository.save(user);
		return true;
	}
}
