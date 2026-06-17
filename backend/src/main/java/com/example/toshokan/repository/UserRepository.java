package com.example.toshokan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.toshokan.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{	
	public Optional<User> findByName(String name);
	public boolean existsByName(String name);
	
	public boolean existsByEmail(String email);
	Optional<User> findByVerifyToken(String token);

}
