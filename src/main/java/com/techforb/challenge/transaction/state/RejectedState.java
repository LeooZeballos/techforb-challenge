package com.techforb.challenge.transaction.state;

/**
 * Rejected state of a transaction.
 * This state is used to indicate that a transaction has been rejected.
 * A transaction can be rejected for many reasons, for example, if the account does not have enough funds to execute the transaction.
 *
 * @see TransactionState
 * @author Leonel Zeballos
 */
public class RejectedState extends TransactionState {

    /**
     * Singleton instance
     */
    private static final RejectedState instance = new RejectedState();


    /**
     * Constructor for the pending state
     */
    private RejectedState() {
        this.setName("REJECTED");
        this.setDescription("The transaction has been rejected.");
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
