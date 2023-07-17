package com.techforb.challenge.account;

import com.techforb.challenge.card.Card;
import com.techforb.challenge.transaction.Transaction;
import com.techforb.challenge.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {

    private Account account;

    @Mock
    private Card card;

    @Mock
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        AccountType accountType = new AccountType();
        User user = new User();
        List<Card> cards = Collections.singletonList(card);
        List<Transaction> transactions = Collections.singletonList(transaction);

        account = Account.builder()
                .id(1L)
                .accountNumber("123456789")
                .createdAt(LocalDateTime.now())
                .balance(1000.0)
                .accountType(accountType)
                .user(user)
                .cards(cards)
                .transactions(transactions)
                .build();
    }

    @Test
    void testAccountProperties() {
        // Test account properties
        assertEquals(1L, account.getId());
        assertEquals("123456789", account.getAccountNumber());
        assertEquals(1000.0, account.getBalance());
        // Test associations
        assertEquals(1, account.getCards().size());
        assertEquals(1, account.getTransactions().size());
    }

}
