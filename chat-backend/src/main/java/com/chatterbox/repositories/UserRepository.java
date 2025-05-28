package com.chatterbox.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chatterbox.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * Finds a user by email.
	 * @param email The email address to seach for.
	 * @return An {@code Optional<User>} containing the user if found, or empty if not.
	 */
	Optional<User> findByEmail(String email);

	/**
	 * Checks if an email already exists in the database.
	 * @param email The email address to check.
	 * @return {@code true} if the email exists, {@code false} otherwise.
	 */
	boolean existsByEmail(String email);
}
