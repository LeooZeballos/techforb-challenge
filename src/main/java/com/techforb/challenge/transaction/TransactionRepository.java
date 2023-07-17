package com.techforb.challenge.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Find all latest transactions by account number.
     */
    Page<Transaction> findAllByAccount_AccountNumberOrderByDateDesc(String accountNumber, Pageable page);
}
