package com.techforb.challenge.card;

import com.techforb.challenge.auth.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Represents a service for cards.
 *
 * @author Leonel Zeballos
 */
@Service
@RequiredArgsConstructor
public class CardServiceImpl implements ICardService {

    /**
     * The repository for cards.
     */
    private final CardRepository cardRepository;

    /**
     * The service for authentication. To get the authenticated user.
     */
    private final IAuthService authService;

    /**
     * Finds all cards owned by the authenticated user.
     *
     * @param jwtAuthenticationToken The authentication token.
     * @return A list of cards owned by the given user.
     */
    @Override
    public List<Card> findAllByOwner(JwtAuthenticationToken jwtAuthenticationToken) {
        // Get the authenticated user id.
        Long ownerId = authService.getCurrentUser(jwtAuthenticationToken).getId();

        // Find all cards owned by the authenticated user.
        return cardRepository.findAllByOwner_Id(ownerId);
    }

}
