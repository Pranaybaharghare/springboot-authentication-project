package com.example.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.User;
import com.example.service.UserService;

@RequestMapping("/users")
@RestController
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/me")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<User> authenticatedUser() {
		User currentUser = new User();
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			 currentUser = (User) authentication.getPrincipal();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(currentUser);

	}

	@GetMapping("/allUsers")
	@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
	public ResponseEntity<List<User>> allUsers() {
		List<User> users = userService.allUsers();

		return ResponseEntity.ok(users);
	}
}
