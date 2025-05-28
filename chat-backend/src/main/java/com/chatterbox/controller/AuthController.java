package com.chatterbox.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatterbox.models.AuthRequest;
import com.chatterbox.models.User;
import com.chatterbox.services.AuthService;

import jakarta.validation.Valid;

/**
 * REST controller for handling authentication requests.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final AuthService authService;

	/**
	 * Constructs and authentication controller with the required dependencies.
	 * @param authService The service handling authentication logic.
	 */
	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
		Optional<User> authenticatedUser = authService.authenticate(authRequest);

		if (authenticatedUser.isPresent()) {
			String token = authService.generateToken(authenticatedUser.get());
			Map<String, String> response = new HashMap<>();
			response.put("token", token);
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.status(401).body("Invalid email or password");
		}
	}

	/**
	 * Creates a new user.
	 * @param user The user to create.
	 * @return The created user.
	 */
	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody User user) {
		try {
			User registeredUser = authService.register(user);
			return ResponseEntity.ok(registeredUser);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage()); // Bad Request
		}
	}
}
