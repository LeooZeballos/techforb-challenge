package com.techforb.challenge.balance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BalanceControllerTest {
    private BalanceController balanceController;

    @Mock
    private BalanceService balanceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        balanceController = new BalanceController(balanceService);
    }

    @Test
    void testGetDailyBalance_Success() {
        // Create test data
        String accountNumber = "123456789";
        LocalDate startDate = LocalDate.of(2023, 7, 1);
        LocalDate endDate = LocalDate.of(2023, 7, 31);
        DailyBalance dailyBalance = new DailyBalance(LocalDate.of(2023, 7, 1), 1000.0);
        List<DailyBalance> expectedBalances = Collections.singletonList(dailyBalance);
        when(balanceService.getDailyBalance(accountNumber, startDate, endDate)).thenReturn(expectedBalances);

        // Perform the test
        ResponseEntity<List<DailyBalance>> response = balanceController.getDailyBalance(accountNumber, startDate, endDate);

        // Verify the response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBalances, response.getBody());

        // Verify that the balanceService.getDailyBalance() method was called
        verify(balanceService, times(1)).getDailyBalance(accountNumber, startDate, endDate);
    }

    @Test
    void testGetDailyBalance_InternalServerError() {
        // Create test data
        String accountNumber = "123456789";
        LocalDate startDate = LocalDate.of(2023, 7, 1);
        LocalDate endDate = LocalDate.of(2023, 7, 31);
        String errorMessage = "Internal Server Error";
        when(balanceService.getDailyBalance(accountNumber, startDate, endDate)).thenThrow(new RuntimeException(errorMessage));

        // Perform the test
        ResponseEntity<List<DailyBalance>> response = balanceController.getDailyBalance(accountNumber, startDate, endDate);

        // Verify the response
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());

        // Verify that the balanceService.getDailyBalance() method was called
        verify(balanceService, times(1)).getDailyBalance(accountNumber, startDate, endDate);
    }

}
