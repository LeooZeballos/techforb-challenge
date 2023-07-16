package com.techforb.challenge.card;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Represents a controller for cards.
 * This class is responsible for handling requests related to cards.
 *
 * @see Card
 * @author LeonelZeballos
 */
@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
@Slf4j
public class CardController {

    /**
     * The service for cards.
     */
    private final ICardService cardService;

    @GetMapping
    public ResponseEntity<List<Card>> findAll(JwtAuthenticationToken jwtAuthenticationToken) {
        try {
            List<Card> cards = cardService.findAllByOwner(jwtAuthenticationToken);
            if (cards.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(cards);
        } catch (Exception e) {
            log.error("Error while finding all cards owned by the user.", e);
            return ResponseEntity.internalServerError().build();
        }
    }

}
