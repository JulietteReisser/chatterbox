package com.chatterbox.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatterbox.models.User;
import com.chatterbox.repositories.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	/**
	 * Constructs a UserService with a UserRepository dependency.
	 * @param userRepository The repository for user data.
	 */
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Finds a user by their email address.
	 * @param email The email of the user.
	 * @return An Optional containing the found user, or empty if not found.
	 */
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	/**
	 * Checks if an email is already registered.
	 * @param email The email to check.
	 * @return {@code true} if the email is already in use, (@code false} otherwise.
	 */
	public boolean emailExists(String email) {
		return userRepository.existsByEmail(email);
	}
}
