package com.example.service;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dto.LoginUserDto;
import com.example.dto.RegisterUserDto;
import com.example.entity.Role;
import com.example.entity.RoleEnum;
import com.example.entity.User;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;

@Service
public class AuthenticationService {
	private UserRepository userRepository;

	private PasswordEncoder passwordEncoder;

	private AuthenticationManager authenticationManager;

	private RoleRepository roleRepository;
	public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager,RoleRepository roleRepository) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.roleRepository = roleRepository;
	}

	public User signup(RegisterUserDto input,String coverImageUploaded) {
	    Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);

	    if (optionalRole.isEmpty()) {
	        return null;
	    }
	        
		User user = new User();
		user.setEmail(input.getEmail());
		user.setFullName(input.getFullName());
		user.setPassword(passwordEncoder.encode(input.getPassword()));
		user.setCoverImage(coverImageUploaded);
		user.setRole(optionalRole.get());

		return userRepository.save(user);
	}

	public User authenticate(LoginUserDto input) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));

		return userRepository.findByEmail(input.getEmail()).orElseThrow();
	}
}
