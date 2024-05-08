package com.example.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class RegisterUserDto {
	private String email;

	private String fullName;

	private String password;

	private MultipartFile coverImage;
}
