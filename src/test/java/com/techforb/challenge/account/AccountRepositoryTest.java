package com.techforb.challenge.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AccountRepositoryTest {

    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByAccountNumber() {
        // Create test data
        String accountNumber = "123456789";
        Account account = new Account();
        account.setAccountNumber(accountNumber);

        // Mock the repository method
        when(accountRepository.findByAccountNumber(accountNumber))
                .thenReturn(Optional.of(account));

        // Perform the test
        Optional<Account> result = accountRepository.findByAccountNumber(accountNumber);

        // Verify the result
        assertEquals(Optional.of(account), result);

        // Verify the repository method was called
        verify(accountRepository, times(1)).findByAccountNumber(accountNumber);
    }

}
