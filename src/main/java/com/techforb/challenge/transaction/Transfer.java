package com.techforb.challenge.transaction;

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
@NoArgsConstructor
@AllArgsConstructor
public class Transfer extends Transaction {

    /**
     * The account number of the destination account.
     */
    private String destinationAccountNumber;

    /**
     * Executes the transfer transaction.
     */
    public void execute() {
        performTransaction(new TransferStrategy());
    }

}
