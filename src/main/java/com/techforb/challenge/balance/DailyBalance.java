package com.techforb.challenge.balance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * Represents the daily balance of an account. Starts at zero and is updated with each transaction that occurs on the account on that day.
 *
 * @author Leonel Zeballos
 */
@Data
@AllArgsConstructor
@Builder
public class DailyBalance {

    /**
     * The date of the balance.
     */
    private LocalDate date;

    /**
     * The balance of the account on the date.
     */
    private double balance;

}
