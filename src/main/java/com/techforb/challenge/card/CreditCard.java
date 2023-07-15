package com.techforb.challenge.card;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

/**
 * Represents a credit card owned by a user.
 *
 * @see Card
 * @author Leonel Zeballos
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("CREDIT_CARD")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard extends Card {

    /**
     * The credit limit of the card.
     */
    private double creditLimit;

}

