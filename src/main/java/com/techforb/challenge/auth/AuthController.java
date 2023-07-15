package com.techforb.challenge.auth;

import com.techforb.challenge.request.LoginRequest;
import com.techforb.challenge.request.RefreshRequest;
import com.techforb.challenge.request.RegisterRequest;
import com.techforb.challenge.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The authentication controller. Contains endpoints for authenticating users.
 * All endpoints are prefixed with /api/v1/auth. The endpoints are not secured.
 *
 * @author Leonel Zeballos
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    /**
     * The authentication service.
     */
    private final IAuthService authService;

    /**
     * Authenticates a user by logging them in.
     *
     * @param loginRequest The login request containing the user's credentials.
     * @return A ResponseEntity containing a TokenResponse with the generated token.
     *         If there's an error, the response body will contain an error message.
     */
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            return ResponseEntity.ok(authService.login(loginRequest));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    TokenResponse.builder().error(e.getMessage()).build());
        } catch (Exception e) {
            log.error("Error while logging in", e);
            return ResponseEntity.internalServerError().body(
                    TokenResponse.builder().error(e.getMessage()).build());
        }
    }

    /**
     * Registers a new user.
     *
     * @param registerRequest The registration request containing the user's details.
     * @return A ResponseEntity containing a TokenResponse with the generated token.
     *         If there's an error, the response body will contain an error message.
     */
    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody RegisterRequest registerRequest) {
        try {
            return ResponseEntity.ok(authService.register(registerRequest));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    TokenResponse.builder().error(e.getMessage()).build());
        } catch (Exception e) {
            log.error("Error while registering", e);
            return ResponseEntity.internalServerError().body(
                    TokenResponse.builder().error(e.getMessage()).build());
        }
    }

    /**
     * Refreshes an access token.
     *
     * @param refreshRequest The refresh request containing the refresh token.
     * @return A ResponseEntity containing a TokenResponse with the new access token.
     *         If there's an error, the response body will contain an error message.
     */
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@RequestBody RefreshRequest refreshRequest) {
        try {
            return ResponseEntity.ok(authService.refreshToken(refreshRequest));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    TokenResponse.builder().error(e.getMessage()).build());
        } catch (Exception e) {
            log.error("Error while refreshing token", e);
            return ResponseEntity.internalServerError().body(
                    TokenResponse.builder().error(e.getMessage()).build());
        }
    }

}
