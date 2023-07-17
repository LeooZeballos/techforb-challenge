package com.techforb.challenge.transaction.state;

/**
 * Completed state of a transaction.
 * This state is used to indicate that a transaction has been completed.
 *
 * @see TransactionState
 * @author Leonel Zeballos
 */
public class CompletedState extends TransactionState {

    /**
     * The instance of the transaction state.
     */
    private static final TransactionState instance = new CompletedState();

    /**
     * Private constructor.
     */
    private CompletedState() {
        this.setName("COMPLETED");
        this.setDescription("The transaction is completed.");
        this.setInitial(false);
        this.setFinal(true);
    }

    /**
     * Get the instance of the transaction state.
     */
    public static TransactionState getInstance() {
        return instance;
    }

}
