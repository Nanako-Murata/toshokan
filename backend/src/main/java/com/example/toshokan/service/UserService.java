package com.example.toshokan.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.toshokan.entity.User;
import com.example.toshokan.repository.UserRepository;

@Service
public class UserService {
	private UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository=userRepository;
		this.passwordEncoder=passwordEncoder;
	}
	
	public boolean check(String name, String password) {
		Optional<User> optionalUser = userRepository.findByName(name);
		if(optionalUser.isEmpty()) {
			return false;
		}
		User user = optionalUser.get();
		return passwordEncoder.matches(password, user.getPassword());
	}
	
	public void signup(String name, String password) {
		User user = new User();
		user.setName(name);
		user.setPassword(passwordEncoder.encode(password));
		userRepository.save(user);
	}

}
