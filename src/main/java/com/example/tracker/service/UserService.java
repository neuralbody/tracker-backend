package com.example.tracker.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.tracker.model.User;
import com.example.tracker.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public User register(String username, String rawPassword) {
        if (userRepository.existsByUsername(username)) throw new RuntimeException("Username already taken");
        String hashed = passwordEncoder.encode(rawPassword);
        User u = User.builder()
				.username(username)
				.password(hashed)
				.build();
        return userRepository.save(u);
    }
	
	
}
