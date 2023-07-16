package com.techforb.challenge.transaction;

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
@Builder
@NoArgsConstructor
public class Withdrawal extends Transaction {

}