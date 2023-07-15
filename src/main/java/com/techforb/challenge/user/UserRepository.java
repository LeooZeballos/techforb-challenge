package com.techforb.challenge.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * The user repository.
 *
 * @author Leonel Zeballos
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find a user by its D.N.I. The D.N.I. is unique.
     *
     * @param dni The D.N.I. to search for.
     * @return An optional containing the user if found, or an empty optional otherwise.
     */
    Optional<User> findByDni(String dni);
}
