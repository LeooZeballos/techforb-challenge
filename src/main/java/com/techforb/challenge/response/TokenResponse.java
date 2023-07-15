package com.techforb.challenge.response;

import lombok.Builder;

/**
 * Represents a token response with a token and an error message.
 * Either token or error will be null.
 *
 * @param token
 * @param error
 */
@Builder
public record TokenResponse(String token, String error) {
}
