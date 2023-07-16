package com.techforb.challenge.card;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.techforb.challenge.account.Account;
import com.techforb.challenge.transaction.Transaction;
import com.techforb.challenge.user.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Represents a card owned by a user.
 * This is an abstract class, so it cannot be instantiated.
 * The card is linked to an account.
 *
 * @author Leonel Zeballos
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "card_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Card {

    /**
     * The unique identifier of the card.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The card number.
     */
    private String cardNumber;

    /**
     * The card expiration date.
     */
    private LocalDate expirationDate;

    /**
     * The card cvv.
     */
    private String cvv;

    /**
     * The card titular.
     */
    private String titular;

    /**
     * The card owner.
     */
    @ManyToOne
    @JsonIgnore
    private User owner;

    /**
     * The card account.
     */
    @ManyToOne
    @JsonIgnore
    private Account account;

    /**
     * The card transactions.
     */
    @OneToMany(mappedBy = "card", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Transaction> transactions;

}

