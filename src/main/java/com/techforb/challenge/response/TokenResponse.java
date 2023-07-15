package com.techforb.challenge.response;

import lombok.Builder;

@Builder
public record TokenResponse(String token, String error) {
}
