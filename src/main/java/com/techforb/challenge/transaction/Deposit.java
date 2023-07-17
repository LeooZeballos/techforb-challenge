package com.techforb.challenge.transaction;

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
@Builder
@NoArgsConstructor
public class Deposit extends Transaction {

    /**
     * Executes the deposit transaction.
     */
    public void execute() {
        super.execute(this, new DepositStrategy());
    }

}
