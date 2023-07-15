package com.techforb.challenge.account;

import jakarta.persistence.*;
import lombok.*;

/**
 * Represents a type of account.
 *
 * @see Account
 * @author Leonel Zeballos
 */
@Entity
@Table(name = "account_type")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountType {

    /**
     * The unique identifier of the account type.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     * The name of the account type.
     */
    @Column(name = "name", nullable = false)
    @Builder.Default
    private String name = "";

    /**
     * The description of the account type.
     */
    @Column(name = "description", nullable = false)
    @Builder.Default
    private String description = "";

}
