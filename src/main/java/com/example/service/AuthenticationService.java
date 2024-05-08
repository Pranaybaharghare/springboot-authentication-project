package com.example.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dto.LoginUserDto;
import com.example.dto.RegisterUserDto;
import com.example.entity.User;
import com.example.repository.UserRepository;

@Service
public class AuthenticationService {
	private UserRepository userRepository;

	private PasswordEncoder passwordEncoder;

	private AuthenticationManager authenticationManager;

	public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
	}

	public User signup(RegisterUserDto input,String coverImageUploaded) {
		User user = new User();
		user.setEmail(input.getEmail());
		user.setFullName(input.getFullName());
		user.setPassword(passwordEncoder.encode(input.getPassword()));
		user.setCoverImage(coverImageUploaded);

		return userRepository.save(user);
	}

	public User authenticate(LoginUserDto input) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));

		return userRepository.findByEmail(input.getEmail()).orElseThrow();
	}
}
