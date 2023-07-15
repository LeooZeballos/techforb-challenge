package com.techforb.challenge.request;

import com.techforb.challenge.user.DocumentType;
import org.springframework.lang.NonNull;

/**
 * A request to register a new user. This class is used to map the request body to an object.
 *
 * @author Leonel Zeballos
 */
public record RegisterRequest(
        @NonNull String dni,
        @NonNull String password,
        @NonNull String email,
        @NonNull String firstName,
        @NonNull String lastName,
        @NonNull DocumentType documentType
) {
}
