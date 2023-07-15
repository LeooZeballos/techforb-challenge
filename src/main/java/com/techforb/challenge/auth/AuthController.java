package com.techforb.challenge.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    /**
     * The authentication service.
     */
    private final IAuthService authService;

    @GetMapping("/login")
    public String hello() {
        return "Hello World!";
    }

}
