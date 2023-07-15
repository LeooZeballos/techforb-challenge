package com.techforb.challenge.card;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;


/**
 * Represents a debit card owned by a user.
 *
 * @see Card
 * @author Leonel Zeballos
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("DEBIT_CARD")
@Data
@Builder
@AllArgsConstructor
public class DebitCard extends Card {
}
