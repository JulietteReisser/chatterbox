package com.chatterbox.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Registers a DAO-based AuthenticationProvider bean used by Spring Security for user authentication.
 * 
 * This provider uses:
 * - a UserDetailsService, which loads user data from the database
 * - a BCryptPasswordEncoder, which checks if the provided password matches the stored hashed password.
 */
@Configuration
public class AuthenticationProviderConfig {

	/**
	 * Creates a DaoAuthenticationProvider bean that uses the provided UserDetailsService and BCryptPasswordEncoder.
	 *
	 * @param userDetailsService The service to load user details from the database.
	 * @param passwordEncoder The encoder to hash and verify passwords.
	 * @return An AuthenticationProvider configured with the provided services.
	 */
	@Bean
	public AuthenticationProvider authenticationProvider(
		UserDetailsService userDetailsService,
		BCryptPasswordEncoder passwordEncoder
	) {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);
		return authenticationProvider;
	}
}
