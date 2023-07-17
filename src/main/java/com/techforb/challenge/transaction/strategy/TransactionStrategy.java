package com.techforb.challenge.transaction.strategy;

import com.techforb.challenge.transaction.Transaction;

/**
 * Transaction strategy interface.
 * This interface defines the methods that can be called on a transaction strategy.
 *
 * @author Leonel Zeballos
 */
public interface TransactionStrategy {

    /**
     * Execute the transaction
     * @param transaction the transaction to execute
     */
    void executeTransaction(Transaction transaction);

}
