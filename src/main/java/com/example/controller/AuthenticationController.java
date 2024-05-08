package com.example.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudinary.Cloudinary;
import com.example.configs.CloudinaryConfiguration;
import com.example.dto.LoginResponse;
import com.example.dto.LoginUserDto;
import com.example.dto.RegisterUserDto;
import com.example.entity.User;
import com.example.service.AuthenticationService;
import com.example.service.JwtService;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
	private JwtService jwtService;

	private CloudinaryConfiguration cloudinaryConfiguration;

	private AuthenticationService authenticationService;

	public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService,
			Cloudinary cloudinary, CloudinaryConfiguration cloudinaryConfiguration) {
		super();
		this.jwtService = jwtService;
		this.authenticationService = authenticationService;
		this.cloudinaryConfiguration = cloudinaryConfiguration;
	}

	@PostMapping("/signup")
	public ResponseEntity<?> register(@ModelAttribute RegisterUserDto registerUserDto) {
		Map<String, Object> coverImageUploaded;
		User registeredUser = new User();
		try {
			if ((registerUserDto.getEmail() == null) && (registerUserDto.getFullName() == null)
					&& (registerUserDto.getPassword() == null) && (registerUserDto.getCoverImage() == null)) {
				throw new ValidationException("Missing required fields");

			}
			coverImageUploaded = cloudinaryConfiguration.uploadOnCloudinary(registerUserDto.getCoverImage());
			registeredUser = authenticationService.signup(registerUserDto,
					(String) coverImageUploaded.get("secure_url"));

		} catch (ValidationException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (RegistrationException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		return ResponseEntity.ok(registeredUser);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
		User authenticatedUser = authenticationService.authenticate(loginUserDto);
		String jwtToken = jwtService.generateToken(authenticatedUser);

		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setToken(jwtToken);
		loginResponse.setExpiresIn(jwtService.getExpirationTime());
		loginResponse.setEmail(authenticatedUser.getEmail());
		loginResponse.setFullName(authenticatedUser.getFullName());

		return ResponseEntity.ok(loginResponse);
	}

	class ValidationException extends RuntimeException {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ValidationException(String message) {
			super(message);
		}
	}

	class RegistrationException extends RuntimeException {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public RegistrationException(String message) {
			super(message);
		}
	}
}
