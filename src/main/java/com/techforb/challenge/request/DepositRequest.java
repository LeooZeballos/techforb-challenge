package com.techforb.challenge.request;

import java.time.LocalDate;

public record DepositRequest(String accountNumber, Double amount, String description, String cardNumber, String cardHolderName, LocalDate cardExpirationDate, String cardCvv) {
}
