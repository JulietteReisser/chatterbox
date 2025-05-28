package com.chatterbox.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AuthRequest {

	/** The email addess of the user attempting to log in. */
	@Email
	@NotBlank
	private String email;

	/** The user's password of the user attempting to log in. */
	@NotBlank
	private String password;

	public AuthRequest() {}

	/**
	 * Constructs an authentication request with an email and a password.
	 * @param email The user's email address.
	 * @param password The user's password.
	 */
	public AuthRequest(String email, String password) {
		this.email = email;
		this.password = password;
	}

	/** @return The user's email address. */
	public String getEmail() {
		return email;
	}

	/** Sets the user's email address. */
	public void setEmail(String email) {
		this.email = email;
	}

	/** @return The user's password. */
	public String getPassword() {
		return password;
	}

	/** Sets the user's password. */
	public void setPassword(String password) {
		this.password = password;
	}
}
