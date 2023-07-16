package com.techforb.challenge.auth;

import com.techforb.challenge.request.LoginRequest;
import com.techforb.challenge.request.RefreshRequest;
import com.techforb.challenge.request.RegisterRequest;
import com.techforb.challenge.response.TokenResponse;
import com.techforb.challenge.user.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

/**
 * The user service interface. It extends {@link UserDetailsService} to be used by Spring Security.
 *
 * @see UserDetailsService
 * @author Leonel Zeballos
 */
public interface IAuthService extends UserDetailsService {

    /**
     * Register a new user. It also generates a JWT token.
     *
     * @param registerRequest The register request.
     * @return The token response.
     */
    TokenResponse register(RegisterRequest registerRequest);

    /**
     * Login an existing user. It also generates a JWT token.
     *
     * @param loginRequest The login request.
     * @return The token response.
     */
    TokenResponse login(LoginRequest loginRequest);

    /**
     * Generate a JWT token.
     *
     * @param user The user.
     * @return The token response.
     */
    TokenResponse generateToken(UserDetails user);

    /**
     * Refresh a JWT token.
     *
     * @param refreshToken The refresh token.
     * @return The token response.
     */
    TokenResponse refreshToken(RefreshRequest refreshToken);

    /**
     * Load a user by username.
     *
     * @param username The username.
     * @return The user details.
     * @throws UsernameNotFoundException If the user is not found.
     */
    User loadUser(String username) throws UsernameNotFoundException;

    /**
     * Get the current user from the JWT token.
     *
     * @param token The JWT token.
     * @return The user details.
     * @throws UsernameNotFoundException If the user is not found.
     */
    User getCurrentUser(JwtAuthenticationToken token) throws UsernameNotFoundException;

}
