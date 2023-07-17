package com.techforb.challenge.request;

import java.time.LocalDate;

public record TransferRequest(String sourceAccountNumber, Double amount, String description, String cardNumber, String cardHolderName, LocalDate cardExpirationDate, String cardCvv, String destinationAccountNumber) {
}
