package com.techforb.challenge.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {

    /**
     * Find an account by its account number
     *
     * @param accountNumber the account number
     */
    Optional<Account> findByAccountNumber(String accountNumber);

}
