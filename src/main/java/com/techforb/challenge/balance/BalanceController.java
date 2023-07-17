package com.techforb.challenge.balance;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller for Daily Balance.
 *
 * @author Leonel Zeballos
 */
@RestController
@RequestMapping("/api/v1/balance")
@RequiredArgsConstructor
@Slf4j
public class BalanceController {

    /**
     * The balance service.
     */
    private final BalanceService balanceService;

    @GetMapping
    public ResponseEntity<List<DailyBalance>> getDailyBalance(
            @RequestParam String accountNumber,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ) {
        try {
            return ResponseEntity.ok(balanceService.getDailyBalance(accountNumber, startDate, endDate));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error getting daily balance", e);
            return ResponseEntity.internalServerError().build();
        }
    }

}
