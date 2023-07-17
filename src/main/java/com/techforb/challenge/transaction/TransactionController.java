package com.techforb.challenge.transaction;

import com.techforb.challenge.request.DepositRequest;
import com.techforb.challenge.request.TransferRequest;
import com.techforb.challenge.request.WithdrawalRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Transaction controller.
 * This class defines the endpoints for the transaction resource.
 *
 * @author Leonel Zeballos
 */
@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
@Slf4j
public class TransactionController {

    /**
     * The transaction service.
     */
    private final ITransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(
            @RequestBody DepositRequest request) {
        try {
            return switch (transactionService.deposit(request)) {
                case SUCCESS -> ResponseEntity.ok(
                        "Deposit successful. Added " + request.amount() + " to account " + request.accountNumber()
                );
                case ERROR -> ResponseEntity.badRequest().body("Deposit failed. An error occurred.");
                case INSUFFICIENT_FUNDS -> null;
            };
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Deposit failed. An error occurred.");
        }

    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody WithdrawalRequest request) {
        try {
            return switch (transactionService.withdrawal(request)) {
                case SUCCESS -> ResponseEntity.ok(
                        "Withdrawal successful. Removed " + request.amount() + " from account " + request.accountNumber()
                );
                case INSUFFICIENT_FUNDS -> ResponseEntity.badRequest().body("Withdrawal failed. Insufficient funds.");
                case ERROR -> ResponseEntity.badRequest().body("Withdrawal failed. An error occurred.");
            };
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Withdrawal failed. An error occurred.");
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequest request) {
        try {
            return switch (transactionService.transfer(request)) {
                case SUCCESS -> ResponseEntity.ok(
                        "Transfer successful. Transferred " + request.amount() + " from account " + request.sourceAccountNumber() + " to account " + request.destinationAccountNumber()
                );
                case INSUFFICIENT_FUNDS -> ResponseEntity.badRequest().body("Transfer failed. Insufficient funds.");
                case ERROR -> ResponseEntity.badRequest().body("Transfer failed. An error occurred.");
            };
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Transfer failed. An error occurred.");
        }
    }

    @GetMapping("/latest")
    public ResponseEntity<Page<Transaction>> getLatestTransactions(
            @RequestParam String accountNumber,
            Pageable page
    ) {
        try {
            return ResponseEntity.ok(transactionService.getLatestTransactions(accountNumber, page));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("An error occurred while getting latest transactions", e);
            return ResponseEntity.internalServerError().build();
        }

    }

}
