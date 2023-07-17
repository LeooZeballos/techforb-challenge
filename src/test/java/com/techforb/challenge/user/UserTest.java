package com.techforb.challenge.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        // Initialize a User instance for testing
        user = User.builder()
                .id(1L)
                .dni("123456789")
                .password("password")
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .documentType(DocumentType.PASAPORTE)
                .roles(Collections.singletonList(Role.USER))
                .build();
    }

    @Test
    void testGetAuthorities() {
        List<Role> roles = user.getRoles();

        assertEquals(1, roles.size());
        assertTrue(roles.contains(Role.USER));
    }

    @Test
    void testGetUsername() {
        String expectedUsername = "PASAPORTE:123456789";
        String actualUsername = user.getUsername();

        assertEquals(expectedUsername, actualUsername);
    }

}