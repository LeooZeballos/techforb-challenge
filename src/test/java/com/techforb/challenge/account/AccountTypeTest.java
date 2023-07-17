package com.techforb.challenge.account;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTypeTest {

    @Test
    void testAccountTypeProperties() {
        // Create test data
        Long id = 1L;
        String name = "Savings";
        String description = "Savings Account";

        // Create an AccountType instance
        AccountType accountType = AccountType.builder()
                .id(id)
                .name(name)
                .description(description)
                .build();

        // Test account type properties
        assertEquals(id, accountType.getId());
        assertEquals(name, accountType.getName());
        assertEquals(description, accountType.getDescription());
    }

}
