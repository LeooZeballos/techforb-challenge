package com.techforb.challenge.transaction;

import com.techforb.challenge.account.Account;
import com.techforb.challenge.card.Card;
import com.techforb.challenge.transaction.strategy.TransactionStrategy;
import com.techforb.challenge.transaction.state.TransactionState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a transaction. This is an abstract class, so it cannot be instantiated.
 *
 * @author Leonel Zeballos
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "transaction_type")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Transaction {

    /**
     * The unique identifier of the transaction.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The transaction amount.
     */
    @Column(name = "amount", nullable = false)
    private double amount;

    /**
     * The transaction date.
     */
    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    /**
     * The transaction description.
     */
    @Column(name = "description")
    @Length(max = 50)
    private String description;

    /**
     * The transaction account.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    /**
     * The transaction card.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Card card;

    /**
     * The transaction status history.
     */
    @OneToMany(mappedBy = "transaction", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TransactionStateHistory> stateHistory;

    /**
     * The account number of the destination account.
     * This field is only used for transfers.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Account destinationAccount;

    /**
     * Constructor with the required fields.
     */
    public Transaction(double amount, String description, Account account, Card card) {
        this.amount = amount;
        this.date = LocalDateTime.now();
        this.description = description;
        this.account = account;
        this.card = card;
        this.stateHistory = new ArrayList<>();
        this.stateHistory.add(TransactionStateHistory.builder()
                .transactionStateName(TransactionState.getInstance("PENDING").getName())
                .transaction(this)
                .build());
    }

    /**
     * Get the current transaction state history
     */
    public TransactionStateHistory getCurrentStateHistory() {
        // Validate that the transaction has a state
        if (stateHistory == null || stateHistory.isEmpty()) {
            throw new IllegalStateException("The transaction does not have a state");
        }
        // Return the current transaction state history
        return stateHistory.get(stateHistory.size() - 1);
    }

    /**
     * Gets the current transaction status.
     *
     * @return The transaction status.
     */
    public TransactionState getCurrentState() {
        // Validate that the transaction has a state
        if (stateHistory == null || stateHistory.isEmpty()) {
            throw new IllegalStateException("The transaction does not have a state");
        }
        // Return the current transaction state
        return TransactionState.getInstance(stateHistory.get(stateHistory.size() - 1).getTransactionStateName());
    }

    /**
     * Sets the current transaction state.
     */
    public void setState(TransactionStateHistory transactionStateHistory) {
        this.stateHistory.add(transactionStateHistory);
    }

    /**
     * Accept the transaction
     */
    public void accept() {
        this.getCurrentState().accept(this);
    }

    /**
     * Reject the transaction
     */
    public void reject() {
        this.getCurrentState().reject(this);
    }

    /**
     * Execute the transaction
     */
    public void execute(TransactionStrategy transactionStrategy) {
        this.getCurrentState().execute(this, transactionStrategy);
    }

    /**
     * Complete the transaction
     */
    public void complete() {
        this.getCurrentState().complete(this);
    }

    /**
     * Error the transaction
     */
    public void error() {
        this.getCurrentState().error(this);
    }

}
