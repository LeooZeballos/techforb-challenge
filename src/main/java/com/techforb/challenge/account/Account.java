package com.techforb.challenge.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.techforb.challenge.card.Card;
import com.techforb.challenge.transaction.Transaction;
import com.techforb.challenge.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a banking account owned by a user.
 *
 * @author Leonel Zeballos
 */
@Entity
@Table(name = "account")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Account {

    /**
     * The unique identifier of the account.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     * The account number.
     */
    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    /**
     * The date when the account was created.
     */
    @Column(name = "created_at", nullable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * The balance of the account.
     */
    @Column(name = "balance", nullable = false)
    @Builder.Default
    private Double balance = 0.0;

    /**
     * The account type.
     */
    @ManyToOne
    @JoinColumn(name = "account_type_id", nullable = false)
    private AccountType accountType;

    /**
     * The account owner.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private User user;

    /**
     * The cards associated with the account.
     */
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @Builder.Default
    @JsonIgnore
    private List<Card> cards = List.of();

    /**
     * The transactions associated with the account.
     */
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @Builder.Default
    @JsonIgnore
    private List<Transaction> transactions = List.of();

}
