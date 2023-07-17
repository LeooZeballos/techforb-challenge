package com.techforb.challenge.balance;

import com.techforb.challenge.transaction.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for Daily Balance.
 *
 * @author Leonel Zeballos
 */
@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    /**
     * The transaction repository.
     */
    private final TransactionRepository transactionRepository;

    /**
     * Calculate the daily balance for a given account number over a period of time.
     *
     * @param accountNumber The account number.
     * @param startDate The start date.
     * @param endDate The end date.
     * @return The daily balance.
     */
    public List<DailyBalance> getDailyBalance(String accountNumber, LocalDate startDate, LocalDate endDate) {

        // If the start date is not provided, use 13 days ago.
        if (startDate == null) {
            startDate = LocalDate.now().minusDays(13);
        }
        // If the end date is not provided, use today.
        if (endDate == null) {
            endDate = LocalDate.now();
        }

        // Validate the dates.
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("The start date must be before the end date");
        }

        // Get all transactions for the account number between the start and end date.
        // They are ordered by date ascending.
        // The account number can be the source or destination of the transaction.
        List<Transaction> transactions = transactionRepository.findAllByAccount_AccountNumberOrDestinationAccount_AccountNumberAndDateBetweenOrderByDateAsc(
                accountNumber,
                accountNumber,
                startDate.atStartOfDay(),
                endDate.atTime(23, 59, 59)
        );

        // Calculate the daily balance for the account number.
        List<DailyBalance> dailyBalances = new ArrayList<>();
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {

            // Get the transactions for the current date.
            List<Transaction> transactionsForCurrentDate = getTransactionsForDate(transactions, currentDate);

            // Calculate the balance for the current date.
            double balance = calculateBalance(transactionsForCurrentDate, accountNumber);

            // Add the daily balance to the list.
            dailyBalances.add(new DailyBalance(currentDate, balance));

            // Move to the next date.
            currentDate = currentDate.plusDays(1);
        }

        // Return the daily balances.
        return dailyBalances;
    }

    /**
     * Filter the transactions for a given date.
     *
     * @param transactions The transactions.
     * @param date The date.
     * @return The transactions for the date.
     */
    private List<Transaction> getTransactionsForDate(List<Transaction> transactions, LocalDate date) {
        return transactions.stream()
                .filter(transaction -> transaction.getDate().toLocalDate().isEqual(date))
                .collect(Collectors.toList());
    }

    /**
     * Calculates the balance for a given day of transactions.
     *
     * @param transactions The transactions for the day.
     * @param accountNumber The account number.
     * @return The balance for the day.
     */
    private double calculateBalance(List<Transaction> transactions, String accountNumber) {
        // Start with a zero balance.
        double balance = 0;

        // Iterate through the transactions and calculate the balance.
        for (Transaction transaction : transactions) {
            if (transaction instanceof Transfer) {
                // If the transaction is a transfer, then the account number is either the source or destination.
                // If the account number is the source, then subtract the amount from the balance.
                // If the account number is the destination, then add the amount to the balance.
                if (transaction.getAccount().getAccountNumber().equals(accountNumber)) {
                    balance -= transaction.getAmount();
                } else {
                    balance += transaction.getAmount();
                }
            } else if (transaction instanceof Withdrawal) {
                // If the transaction is a withdrawal, then subtract the amount from the balance.
                balance -= transaction.getAmount();
            } else if (transaction instanceof Deposit) {
                // If the transaction is a deposit, then add the amount to the balance.
                balance += transaction.getAmount();
            } else {
                throw new RuntimeException("Unknown transaction type.");
            }
        }

        // Return the balance.
        return balance;
    }

}
