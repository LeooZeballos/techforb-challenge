package com.techforb.challenge.transaction.strategy;

import com.techforb.challenge.transaction.Transaction;

public interface TransactionStrategy {
    void executeTransaction(Transaction transaction);
}
