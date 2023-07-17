package com.techforb.challenge.transaction.state;

import com.techforb.challenge.transaction.Transaction;

/**
 * Pending state of a transaction. This is the initial state of a transaction.
 * A transaction in this state can be accepted or rejected.
 *
 * @see TransactionState
 * @author Leonel Zeballos
 */
public class PendingState extends TransactionState {

    /**
     * Singleton instance
     */
    private static final PendingState instance = new PendingState();

    /**
     * Constructor for the pending state
     */
    private PendingState() {
        this.setName("PENDING");
        this.setDescription("Pending state of a transaction. This is the initial state of a transaction. A transaction in this state can be accepted or rejected.");
        this.setInitial(true);
        this.setFinal(false);
    }

    /**
     * Get the singleton instance
     *
     * @return the singleton instance
     */
    public static TransactionState getInstance() {
        return instance;
    }

    /**
     * Accept the transaction
     *
     * @param transaction the transaction to accept
     */
    @Override
    public void accept(Transaction transaction) {
        setNewState(transaction, AcceptedState.getInstance());
    }

    /**
     * Reject the transaction
     *
     * @param transaction the transaction to reject
     */
    @Override
    public void reject(Transaction transaction) {
        setNewState(transaction, RejectedState.getInstance());
    }

}
