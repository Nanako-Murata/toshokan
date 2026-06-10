package com.example.toshokan.service;

import org.springframework.stereotype.Service;

import com.example.toshokan.entity.User;
import com.example.toshokan.repository.UserRepository;

@Service
public class UserService {
	private UserRepository userRepository;
	public UserService(UserRepository userReppository) {
		this.userRepository=userRepository;
	}
	
	public boolean check(String name, String password){
		User user = userRepository.findByNameAndPassword(name, password);
		return user !=null;
	}

}
