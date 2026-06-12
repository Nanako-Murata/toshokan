package com.example.toshokan.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.toshokan.entity.User;
import com.example.toshokan.repository.UserRepository;

@Service
public class UserService {
	private UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
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

	public void signup(String name, String password) {
		if (userRepository.existsByName(name)) {
			throw new IllegalArgumentException("この名前は登録済みです");
		}

		User user = new User();
		user.setName(name);
		user.setPassword(passwordEncoder.encode(password));

		userRepository.save(user);

	}
}
