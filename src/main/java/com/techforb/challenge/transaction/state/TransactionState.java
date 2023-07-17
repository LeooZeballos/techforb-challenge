package com.techforb.challenge.transaction.state;

import com.techforb.challenge.transaction.Transaction;
import com.techforb.challenge.transaction.TransactionStateHistory;
import com.techforb.challenge.transaction.strategy.TransactionStrategy;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Transaction state interface.
 * This interface defines the methods that can be called on a transaction.
 *
 * @author Leonel Zeballos
 */
@Getter
@Setter
public abstract class TransactionState {

    /**
     * The name of the transaction state
     */
    private String name;

    /**
     * The description of the transaction state
     */
    private String description;

    /**
     * Whether the transaction state is initial
     */
    private boolean isInitial;

    /**
     * Whether the transaction state is final
     */
    private boolean isFinal;

    /**
     * Accept the transaction
     *
     * @param transaction the transaction to accept
     */
    public void accept(Transaction transaction) {
        throw new UnsupportedOperationException("Cannot accept transaction in state: " + this.getName());
    }

    /**
     * Reject the transaction
     *
     * @param transaction the transaction to reject
     */
    public void reject(Transaction transaction) {
        throw new UnsupportedOperationException("Cannot reject transaction in state: " + this.getName());
    }

    /**
     * Execute the transaction
     *
     * @param transaction the transaction to execute
     */
    public void execute(Transaction transaction, TransactionStrategy transactionStrategy) {
        throw new UnsupportedOperationException("Cannot execute transaction in state: " + this.getName());
    }

    /**
     * Complete the transaction
     *
     * @param transaction the transaction to complete
     */
    public void complete(Transaction transaction) {
        throw new UnsupportedOperationException("Cannot complete transaction in state: " + this.getName());
    }

    /**
     * Error the transaction
     *
     * @param transaction the transaction to error
     */
    public void error(Transaction transaction) {
        throw new UnsupportedOperationException("Cannot error transaction in state: " + this.getName());
    }

    /**
     * Cancel the transaction
     *
     * @param transaction the transaction to cancel
     */
    public void cancel(Transaction transaction) {
        throw new UnsupportedOperationException("Cannot cancel transaction in state: " + this.getName());
    }

    /**
     * Get the instance of the transaction state.
     * This is a singleton.
     *
     * @return the instance of the transaction state
     */
    public static TransactionState getInstance(String state) {
        return switch (state) {
            case "PENDING" -> PendingState.getInstance();
            case "ACCEPTED" -> AcceptedState.getInstance();
            case "REJECTED" -> RejectedState.getInstance();
            case "CANCELLED" -> CancelledState.getInstance();
            case "COMPLETED" -> CompletedState.getInstance();
            case "ERROR" -> ErrorState.getInstance();
            case "IN_PROGRESS" -> InProgressState.getInstance();
            default -> throw new IllegalArgumentException("Invalid state: " + state);
        };
    }

    protected static void setNewState(Transaction transaction, TransactionState newState) {
        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Set the end date of the current history state to now
        TransactionStateHistory historyState = transaction.getCurrentStateHistory();
        historyState.setEndedAt(now);

        // Create a new history state
        TransactionStateHistory history = TransactionStateHistory.builder()
                .transaction(transaction)
                .transactionStateName(newState.getName())
                .createdAt(now)
                .build();

        // Update the transaction's current history state to the new history state
        transaction.setState(history);
    }

}
