package com.techforb.challenge.transaction;

import com.techforb.challenge.account.Account;
import com.techforb.challenge.card.Card;
import com.techforb.challenge.transaction.strategy.DepositStrategy;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

/**
 * Represents a deposit transaction.
 *
 * @see Transaction
 * @author Leonel Zeballos
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("DEPOSIT")
@Data
@NoArgsConstructor
public class Deposit extends Transaction {

    /**
     * Constructs a deposit transaction.
     */
    public Deposit(double amount, String description, Account accountNumber, Card card) {
        super(amount, description, accountNumber, card);
    }

    /**
     * Executes the deposit transaction.
     */
    public void execute() {
        super.execute(new DepositStrategy());
    }

}
