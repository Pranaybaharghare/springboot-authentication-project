package com.example.dto;

import lombok.Data;

@Data
public class LoginResponse {
	private String token;

	private long expiresIn;

	private String email;

	private String fullName;

	public String getToken() {
		return token;
	}
}
