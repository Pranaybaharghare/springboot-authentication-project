package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.User;

@RestController
public class HomeController {

	@RequestMapping(path = "/getData", method = RequestMethod.GET)
	public String getData() {
		return "hello from server";
	}

	@RequestMapping(path = "/me", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
	public ResponseEntity<User> authenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		User currentUser = (User) authentication.getPrincipal();

		return ResponseEntity.ok(currentUser);
	}
	
	
}
