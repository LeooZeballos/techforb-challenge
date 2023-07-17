package com.techforb.challenge.transaction.state;

import com.techforb.challenge.transaction.Transaction;
import com.techforb.challenge.transaction.strategy.TransactionStrategy;

/**
 * Acepted state of a transaction.
 * This state is used to indicate that a transaction has been accepted and is ready to be executed.
 *
 * @see TransactionState
 * @author Leonel Zeballos
 */
public class AcceptedState extends TransactionState {

    /**
     * The instance of the transaction state.
     */
    private static final TransactionState instance = new AcceptedState();

    /**
     * Private constructor.
     */
    private AcceptedState() {
        this.setName("ACCEPTED");
        this.setDescription("The transaction has been accepted and is ready to be executed.");
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
     * Execute the transaction
     *
     * @param transaction the transaction to execute
     */
    @Override
    public void execute(Transaction transaction, TransactionStrategy transactionStrategy) {
        setNewState(transaction, InProgressState.getInstance());
        transactionStrategy.executeTransaction(transaction);
    }

}
