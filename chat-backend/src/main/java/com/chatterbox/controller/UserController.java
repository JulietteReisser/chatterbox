package com.chatterbox.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatterbox.models.User;
import com.chatterbox.services.UserService;

import jakarta.validation.constraints.*;

/**
 * REST controller for managing user-related API requests.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	/**
	 * Constructs a UserController with a UserService dependency.
	 * @param userService The service handling user operations.
	 */
	public UserController(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Retrieves a user by email.
	 * @param email The email of the user to retrieve.
	 * @return The user if found, or 404 Not Found.
	 */
	@GetMapping("/{email}")
	public ResponseEntity<User> getUserByEmail(@PathVariable @Email String email) {
		Optional<User> user = userService.findByEmail(email);
		return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	/**
	 * Checks if an email is already registered.
	 * @param email The email to check.
	 * @return `true` if the email exists, `false` otherwise.
	 */
	@GetMapping("/exists/{email}")
	public ResponseEntity<Boolean> checkEmailExists(@PathVariable @Email String email) {
		boolean exists = userService.emailExists(email);
		return ResponseEntity.ok(exists);
	}
}
