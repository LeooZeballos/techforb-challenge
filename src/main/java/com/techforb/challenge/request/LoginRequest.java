package com.techforb.challenge.request;

import org.springframework.lang.NonNull;

/**
 * A request to login. This class is used to map the request body to an object.
 *
 * @author Leonel Zeballos
 */
public record LoginRequest(
        @NonNull String dni,
        @NonNull String documentType,
        @NonNull String password
) {
}
