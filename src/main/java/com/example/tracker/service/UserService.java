package com.example.tracker.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.tracker.dto.AuthRequest;
import com.example.tracker.model.User;
import com.example.tracker.repository.UserRepository;
import com.example.tracker.security.JwtUtils;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtils jwtUtils;
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtils = jwtUtils;
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
	
	public String login(AuthRequest request) {
        Optional<User> opt = userRepository.findByUsername(request.getUsername());
        this.log.info("Attempting login for user: " + request.getUsername());
        if (opt.isEmpty()) {
            throw new IllegalArgumentException("No user found with username: " + request.getUsername());
        }
        User user = opt.get();
        this.log.info("User found: " + user.getUsername());
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
        // create token with subject = username
        return jwtUtils.generateToken(user.getUsername());
    }
	
	
}
