package com.techforb.challenge.transaction;

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

}
