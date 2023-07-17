package com.techforb.challenge.transaction;

import com.techforb.challenge.account.Account;
import com.techforb.challenge.card.Card;
import com.techforb.challenge.transaction.strategy.WithdrawalStrategy;
import jakarta.persistence.*;
import lombok.*;

/**
 * Represents a withdrawal transaction.
 *
 * @see Transaction
 * @author Leonel Zeballos
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("WITHDRAWAL")
@Data
@NoArgsConstructor
public class Withdrawal extends Transaction {

    /**
     * Constructs a withdrawal transaction.
     */
    public Withdrawal(double amount, String description, Account accountNumber, Card card) {
        super(amount, description, accountNumber, card);
    }

    /**
     * Executes the withdrawal transaction.
     */
    public void execute() {
        super.execute(new WithdrawalStrategy());
    }

}
