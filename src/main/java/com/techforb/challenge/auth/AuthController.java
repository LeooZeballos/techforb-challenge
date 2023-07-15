package com.techforb.challenge.auth;

import com.techforb.challenge.request.LoginRequest;
import com.techforb.challenge.request.RefreshRequest;
import com.techforb.challenge.request.RegisterRequest;
import com.techforb.challenge.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    /**
     * The authentication service.
     */
    private final IAuthService authService;

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
