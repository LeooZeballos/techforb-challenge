package com.techforb.challenge.transaction;

import com.techforb.challenge.account.Account;
import com.techforb.challenge.account.AccountRepository;
import com.techforb.challenge.card.Card;
import com.techforb.challenge.card.CardRepository;
import com.techforb.challenge.request.DepositRequest;
import com.techforb.challenge.request.TransferRequest;
import com.techforb.challenge.request.WithdrawalRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Transaction service implementation.
 *
 * @author Leonel Zeballos
 */
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements ITransactionService {

    /**
     * The transaction repository.
     */
    private final TransactionRepository transactionRepository;

    /**
     * The account repository.
     */
    private final AccountRepository accountRepository;

    /**
     * The card repository.
     */
    private final CardRepository cardRepository;

    /**
     * Make a deposit transaction.
     *
     * @param request the deposit request.
     */
    @Override
    @Transactional
    public TransactionCode deposit(DepositRequest request) {
        // Get the account from the repository.
        Account account = accountRepository.findByAccountNumber(request.accountNumber())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Get the card from the repository.
        Card card = cardRepository.findByCardNumberAndExpirationDateAndCvvAndTitular(
                request.cardNumber(),
                request.cardExpirationDate(),
                request.cardCvv(),
                request.cardHolderName()
                )
                .orElseThrow(() -> new RuntimeException("Card not found"));

        // Create the Deposit transaction.
        Deposit deposit = new Deposit(
                request.amount(),
                request.description(),
                account,
                card
        );

        // Accept the transaction.
        deposit.accept();

        // Execute the transaction.
        try {
            deposit.execute();
        } catch (Exception e) {
            // If the transaction failed, error it.
            deposit.error();
            // Save the transaction.
            transactionRepository.save(deposit);
            // Return error.
            return TransactionCode.ERROR;
        }

        // If the transaction was successful, complete it.
        deposit.complete();

        // Save the account.
        accountRepository.save(account);

        // Save the transaction.
        transactionRepository.save(deposit);

        return TransactionCode.SUCCESS;
    }

    /**
     * Make a withdrawal transaction.
     *
     * @param request the withdrawal request.
     */
    @Override
    @Transactional
    public TransactionCode withdrawal(WithdrawalRequest request) {
        // Get the account from the repository.
        Account account = accountRepository.findByAccountNumber(request.accountNumber())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Get the card from the repository.
        Card card = cardRepository.findByCardNumberAndExpirationDateAndCvvAndTitular(
                request.cardNumber(),
                request.cardExpirationDate(),
                request.cardCvv(),
                request.cardHolderName()
                )
                .orElseThrow(() -> new RuntimeException("Card not found"));

        // Create the Withdrawal transaction.
        Withdrawal withdrawal = new Withdrawal(
                request.amount(),
                request.description(),
                account,
                card
        );

        // Check if the account has enough balance.
        if (account.getBalance() < withdrawal.getAmount()) {
            // Reject the transaction.
            withdrawal.reject();
            // Save the transaction.
            transactionRepository.save(withdrawal);
            // Return insufficient funds.
            return TransactionCode.INSUFFICIENT_FUNDS;
        }

        // Accept the transaction.
        withdrawal.accept();

        // Execute the transaction.
        try {
            withdrawal.execute();
        } catch (Exception e) {
            // If the transaction failed, error it.
            withdrawal.error();
            // Save the transaction.
            transactionRepository.save(withdrawal);
            // Return error.
            return TransactionCode.ERROR;
        }

        // If the transaction was successful, complete it.
        withdrawal.complete();

        // Save the account.
        accountRepository.save(account);

        // Save the transaction.
        transactionRepository.save(withdrawal);

        // Return success.
        return TransactionCode.SUCCESS;
    }

    /**
     * Make a transfer transaction.
     *
     * @param request the transfer request.
     */
    @Override
    @Transactional
    public TransactionCode transfer(TransferRequest request) {
        // Get the source account from the repository.
        Account sourceAccount = accountRepository.findByAccountNumber(request.sourceAccountNumber())
                .orElseThrow(() -> new RuntimeException("Source account not found"));

        // Get the destination account from the repository.
        Account destinationAccount = accountRepository.findByAccountNumber(request.destinationAccountNumber())
                .orElseThrow(() -> new RuntimeException("Destination account not found"));

        // Get the card from the repository.
        Card card = cardRepository.findByCardNumberAndExpirationDateAndCvvAndTitular(
                request.cardNumber(),
                request.cardExpirationDate(),
                request.cardCvv(),
                request.cardHolderName()
                )
                .orElseThrow(() -> new RuntimeException("Card not found"));

        // Create the Transfer transaction.
        Transfer transfer = new Transfer(
                request.amount(),
                request.description(),
                sourceAccount,
                destinationAccount,
                card
        );

        // Check if the source account has enough balance.
        if (sourceAccount.getBalance() < transfer.getAmount()) {
            // Reject the transaction.
            transfer.reject();
            // Save the transaction.
            transactionRepository.save(transfer);
            // Return insufficient funds.
            return TransactionCode.INSUFFICIENT_FUNDS;
        }

        // Accept the transaction.
        transfer.accept();

        // Execute the transaction.
        try {
            transfer.execute();
        } catch (Exception e) {
            // If the transaction failed, error it.
            transfer.error();
            // Save the transaction.
            transactionRepository.save(transfer);
            // Return error.
            return TransactionCode.ERROR;
        }

        // If the transaction was successful, complete it.
        transfer.complete();

        // Save the source account.
        accountRepository.save(sourceAccount);

        // Save the destination account.
        accountRepository.save(destinationAccount);

        // Save the transaction.
        transactionRepository.save(transfer);

        // Return success.
        return TransactionCode.SUCCESS;
    }

    /**
     * Get the latest transactions.
     *
     * @param page the page.
     */
    @Override
    public Page<Transaction> getLatestTransactions(String accountNumber, Pageable page) {
        return transactionRepository.findAllByAccount_AccountNumberOrderByDateDesc(accountNumber, page);
    }

}
