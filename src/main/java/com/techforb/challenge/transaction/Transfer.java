package com.techforb.challenge.transaction;

import com.techforb.challenge.account.Account;
import com.techforb.challenge.card.Card;
import com.techforb.challenge.transaction.strategy.TransferStrategy;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

/**
 * Represents a transfer transaction. This is a type of transaction where money
 * is transferred from one account to another.
 *
 * @see Transaction
 * @author Leonel Zeballos
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("TRANSFER")
@Data
@Builder
@AllArgsConstructor
public class Transfer extends Transaction {

    /**
     * Constructs a transfer transaction.
     */
    public Transfer(double amount, String description, Account account, Account destinationAccount, Card card) {
        super(amount, description, account, card, destinationAccount);
    }

    /**
     * Executes the transfer transaction.
     */
    public void execute() {
        super.execute(new TransferStrategy());
    }

}
