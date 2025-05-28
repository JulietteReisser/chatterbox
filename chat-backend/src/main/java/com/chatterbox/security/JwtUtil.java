package com.chatterbox.security;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/**
 * Utility class for generating and validating JWT tokens.
 * Provides methods to create tokens, extract claims, and validate expiration and identity.
 */
@Component
public class JwtUtil {
	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private long expiration;

	/**
	 * Generates a JWT token for the given email.
	 * @param email The email to include as the token subject.
	 * @return A signed JWT token.
	 */
	public String generateToken(String email) {
		return Jwts.builder()
			.setSubject(email)
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + expiration))
			.signWith(getSigningKey(), SignatureAlgorithm.HS256)
			.compact();
	}

	/**
	 * Extracts the email (subject) from a JWT token.
	 * @param token The JWT token.
	 * @return The email (subject) from the token.
	 */
	public String extractEmail(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	/**
	 * Validates the JWT token by checking if it matches the expected email and is not expired.
	 * @param token The JWT token.
	 * @param expectedEmail The email to validate against the token's subject.
	 * @return True if the token is valid and matches the email, false otherwise.
	 */
	public boolean validate(String token, String expectedEmail) {
		return extractEmail(token).equals(expectedEmail) && !isTokenExpired(token);
	}

	/**
	 * Extracts a specific claim from the JWT token using a resolver function.
	 * @param token The JWT token.
	 * @param claimsResolver A function to extract a specific claim from the token's claims.
	 * @param <T> The type of the extracted claim.
	 * @return The extracted claim.
	 */
	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		Claims claims = Jwts.parserBuilder()
			.setSigningKey(getSigningKey())
			.build()
			.parseClaimsJws(token)
			.getBody();
		return claimsResolver.apply(claims);
	}

	/**
	 * Checks whether the token has expired.
	 * @param token The JWT token.
	 * @return True if the token is expired, false otherwise.
	 */
	private boolean isTokenExpired(String token) {
		return extractClaim(token, Claims::getExpiration).before(new Date());
	}

	/**
	 * Generates the signing key based on the secret key from configuration.
	 * @return The HMAC signing key.
	 */
	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(secret.getBytes());
	}
}
