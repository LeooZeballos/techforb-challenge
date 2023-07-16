package com.techforb.challenge.card;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.List;

/**
 * Represents a service for cards.
 *
 * @author Leonel Zeballos
 */
public interface ICardService {

    /**
     * Finds all cards owned by the authenticated user.
     *
     * @return A list of cards owned by the given user.
     */
    List<Card> findAllByOwner(JwtAuthenticationToken jwtAuthenticationToken);

}
