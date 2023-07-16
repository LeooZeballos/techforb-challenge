package com.techforb.challenge.card;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

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

}
