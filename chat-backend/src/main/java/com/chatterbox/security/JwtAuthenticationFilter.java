package com.chatterbox.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filter that intercepts HTTP requests to check for JWT tokens in the Authorization header.
 * If the token is valid and no authentication is set yet, it sets the SecurityContext accordingly.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final CustomUserDetailsService userDetailsService;

	/**
	 * Constructor to initialize the JwtAuthenticationFilter with JwtUtil and CustomUserDetailsService.
	 *
	 * @param jwtUtil The utility class for JWT operations
	 * @param userDetailsService The service to load user details
	 */
	public JwtAuthenticationFilter(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}

		/**
		 * Filters each request to perform JWT-based authentication.
		 *
		 * @param request The incoming HTTP request
		 * @param response The HTTP response
		 * @param chain The filter chain to pass the request and response to the next filter
		 * @throws ServletException If an error occurs during request processing
		 * @throws IOException If an I/O error occurs during request processing
		 */
	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain)
			throws ServletException, IOException {
		
		// Retrieve the Authorization header from the incoming HTTP request
		String authHeader = request.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			chain.doFilter(request, response);
			return;
		}

		/* Authentication process */
		String token = authHeader.substring(7); // Remove the "Bearer " prefix
		String userEmail = jwtUtil.extractEmail(token);

		// Proceed only if the email is present and no authentication is already set in the context
		if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

			if (jwtUtil.validate(token, userEmail)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		/* ---------------------- */

		chain.doFilter(request, response);
	}
}
