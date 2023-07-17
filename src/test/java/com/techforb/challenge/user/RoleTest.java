package com.techforb.challenge.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RoleTest {

    @Test
    void testEnumValues() {
        Role[] roles = Role.values();

        assertEquals(2, roles.length);
        assertTrue(containsRole(roles, Role.ADMIN));
        assertTrue(containsRole(roles, Role.USER));
    }

    private boolean containsRole(Role[] roles, Role role) {
        for (Role r : roles) {
            if (r.equals(role)) {
                return true;
            }
        }
        return false;
    }

}