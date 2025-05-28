package com.chatterbox.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.chatterbox.models.AuthRequest;
import com.chatterbox.models.User;
import com.chatterbox.repositories.UserRepository;
import com.chatterbox.security.JwtUtil;

/**
 * Service for handling authentication logic.
 */
@Service
public class AuthService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	/**
	 * Constructs an authentication service with the required dependencies.
	 * @param userRepository The repository to fetch user data.
	 */
	@Autowired
	public AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
	}

	/**
	 * Authenticates a user by verifying their email and password.
	 * @param authRequest The authentication request containing email and password.
	 * @return An {@code Optional<User>} containing the authenticated user if successful, or empty if authentication fails.
	 */
	public Optional<User> authenticate(AuthRequest authRequest) {
		return userRepository.findByEmail(authRequest.getEmail())
						.filter(user -> passwordEncoder.matches(authRequest.getPassword(), user.getPassword()));
	}

	/**
	 * Registers a new user by saving their details in the database.
	 * @param user The user to register.
	 * @return The registered user.
	 * @throws IllegalArgumentException if the email already exists.
	 */
	public User register(User user) {
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new IllegalArgumentException("Email already exists");
		}

		String rawPassword = user.getPassword();
		String hashedPassword = passwordEncoder.encode(rawPassword);

		user.setPassword(hashedPassword);
		return userRepository.save(user);
	}

	/**
	 * Generates a JWT token for the authenticated user.
	 * @param user The authenticated user.
	 * @return A JWT token as a string.
	 */
	public String generateToken(User user) {
		return jwtUtil.generateToken(user.getEmail());
	}
}
