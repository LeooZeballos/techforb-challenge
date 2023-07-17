package com.techforb.challenge.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Find all latest transactions by account number.
     */
    Page<Transaction> findAllByAccount_AccountNumberOrderByDateDesc(String accountNumber, Pageable page);

    /**
     * Find all transactions by account number between two dates.
     * The account number can be the source or destination of the transaction.
     * They are ordered by date ascending.
     */
    List<Transaction> findAllByAccount_AccountNumberOrDestinationAccount_AccountNumberAndDateBetweenOrderByDateAsc(
            String accountNumber,
            String destinationAccountNumber,
            LocalDateTime startDate,
            LocalDateTime endDate
    );

}
