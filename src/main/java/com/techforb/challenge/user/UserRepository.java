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
     * Find a user by its D.N.I and DocumentType. The combination of both fields is unique.
     *
     * @param dni The D.N.I. to search for.
     * @param documentType The DocumentType to search for.
     * @return An optional containing the user if found, or an empty optional otherwise.
     */
    Optional<User> findByDocumentTypeAndDni(DocumentType documentType, String dni);

    /**
     * Check if a user exists by its D.N.I and DocumentType.
     *
     * @param dni The D.N.I. to search for.
     * @param documentType The DocumentType to search for.
     * @return True if the user exists, false otherwise.
     */
    boolean existsByDniAndDocumentType(String dni, DocumentType documentType);

    /**
     * Check if a user exists by its email.
     *
     * @param email The email to search for.
     * @return True if the user exists, false otherwise.
     */
    boolean existsByEmail(String email);

}
