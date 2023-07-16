package com.techforb.challenge.transaction;

import com.techforb.challenge.account.Account;
import com.techforb.challenge.card.Card;
import com.techforb.challenge.transaction.strategy.TransactionStrategy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
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
@Builder
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
    @Column(name = "description", nullable = false)
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
    @OneToMany(mappedBy = "transaction", fetch = FetchType.LAZY)
    @Builder.Default
    private List<TransactionStatusHistory> statusHistory = List.of();

    /**
     * Performs the transaction.
     *
     * @param strategy The strategy to use to perform the transaction.
     */
    public void performTransaction(TransactionStrategy strategy) {
        strategy.executeTransaction(this);
    }

}
