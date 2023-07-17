package com.techforb.challenge.balance;

import java.time.LocalDate;
import java.util.List;

public interface BalanceService {

    /**
     * Get the daily balance for the time period.
     */
    List<DailyBalance> getDailyBalance(String accountNumber, LocalDate startDate, LocalDate endDate);

}
