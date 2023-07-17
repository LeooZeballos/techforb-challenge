package com.techforb.challenge.card;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Represents a repository for cards.
 */
public interface CardRepository extends JpaRepository<Card, Long> {

    /**
     * Finds all cards owned by the given user.
     *
     * @param owner The user owner of the cards.
     * @return A list of cards owned by the given user.
     */
    List<Card> findAllByOwner_Id(Long owner);

    /**
     * Finds a card by its number, expiration date, cvv and titular.
     *
     * @param cardNumber The card number.
     * @param expirationDate The card expiration date.
     * @param cvv The card cvv.
     * @param titular The card titular.
     * @return The card with the given number, expiration date, cvv and titular.
     */
    Optional<Card> findByCardNumberAndExpirationDateAndCvvAndTitular(String cardNumber, LocalDate expirationDate, String cvv, String titular);

}
