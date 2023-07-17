package com.techforb.challenge.transaction.state;

import com.techforb.challenge.transaction.Transaction;

/**
 * In progress state.
 * This state is used to indicate that a transaction is in progress.
 *
 * @see TransactionState
 * @author Leonel Zeballos
 */
public class InProgressState extends TransactionState {

    /**
     * The instance of the transaction state.
     */
    private static final TransactionState instance = new InProgressState();

    /**
     * Private constructor.
     */
    private InProgressState() {
        this.setName("IN_PROGRESS");
        this.setDescription("The transaction is in progress.");
        this.setInitial(false);
        this.setFinal(false);
    }

    /**
     * Get the instance of the transaction state.
     */
    public static TransactionState getInstance() {
        return instance;
    }

    /**
     * Complete the transaction
     *
     * @param transaction the transaction to complete
     */
    @Override
    public void complete(Transaction transaction) {
        setNewState(transaction, CompletedState.getInstance());
    }

    /**
     * Error the transaction
     *
     * @param transaction the transaction to error
     */
    @Override
    public void error(Transaction transaction) {
        setNewState(transaction, ErrorState.getInstance());
    }

}
