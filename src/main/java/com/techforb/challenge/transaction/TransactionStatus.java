package com.techforb.challenge.transaction;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "transaction_status_history")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionStatus {

    /**
    * The unique identifier of the transaction status.
    */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String id;

    /**
    * The name of the transaction status.
    */
    @Column(name = "name", nullable = false)
    @Length(max = 50)
    private String name;

    /**
    * The description of the transaction status.
    */
    @Column(name = "description", nullable = false)
    @Length(max = 255)
    private String description;

}
