package com.techforb.challenge.request;

import org.springframework.lang.NonNull;

/**
 * A request to refresh a token. This class is used to map the request body to an object.
 *
 * @param token The token to refresh.
 * @author Leonel Zeballos
 */
public record RefreshRequest(
        @NonNull  String token
) {
}
