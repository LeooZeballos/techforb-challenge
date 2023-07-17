package com.techforb.challenge.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_status_history")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionStateHistory {

    /**
     * The unique identifier of the transaction status history.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     * The name of the transaction state.
     */
    @Column(name = "transaction_state_name", nullable = false)
    private String transactionStateName;

    /**
     * The transaction.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false)
    @JsonIgnore
    private Transaction transaction;

    /**
     * The date when the transaction status history was created.
     */
    @Column(name = "created_at", nullable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * The date when the transaction status history was ended.
     * This field is null if the transaction status history is still active.
     */
    @Column(name = "ended_at")
    private LocalDateTime endedAt;

}
