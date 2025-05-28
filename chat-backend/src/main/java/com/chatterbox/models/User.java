package com.chatterbox.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * Represents a user entity stored in the database.
 * Each user has a unique email and a password.
 */
@Entity
@Table(name="users")
public class User {
	
	/** Unique identifier for the user (auto-generated). */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The user's unique email address. */
	@Email
	@NotBlank
	@Column(nullable = false, unique = true)
	private String email;

	/** The user's password (should be stored encrypted) */
	@NotBlank
	@Column(nullable = false)
	private String password;

	public User() {}

	/**
	 * Creates a user using an email and a password.
	 * @param email The email address of the user.
	 * @param password The user's password.
	 */
	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}

	/** @return The user's ID. */
	public Long getId() {
		return this.id;
	}

	/** Sets the user's ID. */
	public void setId(Long id) {
		this.id = id;
	}

	/** @return The user's email address. */
	public String getEmail() {
		return this.email;
	}

	/** Sets the user's email address. */
	public void setEmail(String email) {
		this.email = email;
	}

	/** @return The user's password. */
	public String getPassword() {
		return this.password;
	}

	/** Sets the user's password. */
	public void setPassword(String password) {
		this.password = password;
	}
}
