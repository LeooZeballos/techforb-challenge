package com.techforb.challenge.auth;

import com.techforb.challenge.request.LoginRequest;
import com.techforb.challenge.request.RegisterRequest;
import com.techforb.challenge.response.TokenResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * The user service interface. It extends {@link UserDetailsService} to be used by Spring Security.
 *
 * @see UserDetailsService
 * @author Leonel Zeballos
 */
public interface IAuthService extends UserDetailsService {
    TokenResponse register(RegisterRequest registerRequest);

    TokenResponse login(LoginRequest loginRequest);

    TokenResponse generateToken(UserDetails user);

    TokenResponse refreshToken(String refreshToken);
}
