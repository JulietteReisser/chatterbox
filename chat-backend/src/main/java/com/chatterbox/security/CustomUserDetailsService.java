package com.chatterbox.security;

import com.chatterbox.models.User;
import com.chatterbox.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom implementation of Spring Security's UserDetailsService interface.
 * This service is used by the authentication provider to load user-specific data.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	/**
	 * Constructor that injects the UserRepository dependency.
	 *
	 * @param userRepository The repository used to fetch User entities from the database.
	 */
	@Autowired
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * oads the user based on their email address.
	 * This method is automatically called during authentication by Spring Security.
	 *
	 * @param email The email address of the user.
	 * @return UserDetails object built from the User entity.
	 * @throws UsernameNotFoundException If no user with the given email is found .
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

		// Converts the User entity to Spring Security's UserDetails implementation
		return org.springframework.security.core.userdetails.User.builder()
			.username(user.getEmail())
			.password(user.getPassword())
			.roles("USER")
			.build();
	}
}
