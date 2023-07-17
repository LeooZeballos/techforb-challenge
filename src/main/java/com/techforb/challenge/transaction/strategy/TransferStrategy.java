package com.techforb.challenge.transaction.strategy;

import com.techforb.challenge.transaction.Transaction;

/**
 * Transfer strategy.
 * This strategy is used to execute a transfer transaction, which is a transaction that moves money from one account to another.
 *
 * @author Leonel Zeballos
 */
public class TransferStrategy implements TransactionStrategy {

    @Override
    public void executeTransaction(Transaction transaction) {
        // Subtract the amount from the source account
        transaction.getAccount().setBalance(transaction.getAccount().getBalance() - transaction.getAmount());

        // Add the amount to the destination account
        transaction.getDestinationAccount().setBalance(transaction.getDestinationAccount().getBalance() + transaction.getAmount());
    }

}
