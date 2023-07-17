package com.techforb.challenge.transaction.state;

/**
 * Cancelled state of a transaction.
 * This state is used to indicate that a transaction has been cancelled.
 *
 * @see TransactionState
 * @author Leonel Zeballos
 */
public class CancelledState extends TransactionState {

    /**
     * Singleton instance
     */
    private static final CancelledState instance = new CancelledState();

    /**
     * Constructor for the pending state
     */
    private CancelledState() {
        this.setName("CANCELLED");
        this.setDescription("The transaction has been cancelled.");
        this.setInitial(false);
        this.setFinal(true);
    }

    /**
     * Get the singleton instance
     *
     * @return the singleton instance
     */
    public static TransactionState getInstance() {
        return instance;
    }

}
