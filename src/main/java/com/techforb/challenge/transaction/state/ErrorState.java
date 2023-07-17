package com.techforb.challenge.transaction.state;

/**
 * Error state of a transaction.
 * This state is used to indicate that a transaction has an error.
 *
 * @see TransactionState
 * @author Leonel Zeballos
 */
public class ErrorState extends TransactionState {

    /**
     * Singleton instance
     */
    private static final ErrorState instance = new ErrorState();

    /**
     * Constructor for the pending state
     */
    private ErrorState() {
        this.setName("ERROR");
        this.setDescription("The transaction has an error.");
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
