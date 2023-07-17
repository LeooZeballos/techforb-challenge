package com.techforb.challenge.transaction;

import com.techforb.challenge.request.DepositRequest;
import com.techforb.challenge.request.TransferRequest;
import com.techforb.challenge.request.WithdrawalRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The transaction service.
 *
 * @see Transaction
 * @author Leonel Zeballos
 */
public interface ITransactionService {

    /**
     * Make a deposit transaction.
     *
     * @param request the deposit request.
     */
    TransactionCode deposit(DepositRequest request);

    /**
     * Make a withdrawal transaction.
     *
     * @param request the withdrawal request.
     */
    TransactionCode withdrawal(WithdrawalRequest request);

    /**
     * Make a transfer transaction.
     */
    TransactionCode transfer(TransferRequest request);

    /**
     * Get the latest transactions.
     *
     * @param page the page.
     * @param accountNumber the account number.
     */
    Page<Transaction> getLatestTransactions(String accountNumber, Pageable page);

}
