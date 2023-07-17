package com.techforb.challenge.card;

import com.techforb.challenge.auth.IAuthService;
import com.techforb.challenge.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CardServiceImplTest {
    private CardServiceImpl cardService;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private IAuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cardService = new CardServiceImpl(cardRepository, authService);
    }

    @Test
    void testFindAllByOwner() {
        JwtAuthenticationToken jwtAuthenticationToken = mock(JwtAuthenticationToken.class);
        Card ownerCard1 = new CreditCard();
        Card ownerCard2 = new DebitCard();
        List<Card> ownerCards = new ArrayList<>();
        ownerCards.add(ownerCard1);
        ownerCards.add(ownerCard2);

        when(authService.getCurrentUser(jwtAuthenticationToken)).thenReturn(User.builder().id(1L).build());
        when(cardRepository.findAllByOwner_Id(1L)).thenReturn(ownerCards);

        List<Card> result = cardService.findAllByOwner(jwtAuthenticationToken);

        assertEquals(ownerCards, result);

        verify(authService, times(1)).getCurrentUser(jwtAuthenticationToken);
        verify(cardRepository, times(1)).findAllByOwner_Id(1L);
    }
}
