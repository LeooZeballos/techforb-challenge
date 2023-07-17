package com.techforb.challenge.card;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@WithMockUser(username = "DNI:123123", password = "123123")
class CardControllerTest {
    private CardController cardController;

    @Mock
    private ICardService cardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cardController = new CardController(cardService);
    }

    @Test
    void testFindAll_ReturnsEmptyList() {
        JwtAuthenticationToken jwtAuthenticationToken = mock(JwtAuthenticationToken.class);
        List<Card> emptyList = new ArrayList<>();
        when(cardService.findAllByOwner(jwtAuthenticationToken)).thenReturn(emptyList);

        ResponseEntity<List<Card>> responseEntity = cardController.findAll(jwtAuthenticationToken);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        verify(cardService, times(1)).findAllByOwner(jwtAuthenticationToken);
    }

    @Test
    void testFindAll_ReturnsCardList() {
        JwtAuthenticationToken jwtAuthenticationToken = mock(JwtAuthenticationToken.class);
        List<Card> cardList = createCardList();
        when(cardService.findAllByOwner(jwtAuthenticationToken)).thenReturn(cardList);

        ResponseEntity<List<Card>> responseEntity = cardController.findAll(jwtAuthenticationToken);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(cardList, responseEntity.getBody());

        verify(cardService, times(1)).findAllByOwner(jwtAuthenticationToken);
    }

    @Test
    void testFindAll_InternalServerError() {
        JwtAuthenticationToken jwtAuthenticationToken = mock(JwtAuthenticationToken.class);
        when(cardService.findAllByOwner(jwtAuthenticationToken)).thenThrow(new RuntimeException());

        ResponseEntity<List<Card>> responseEntity = cardController.findAll(jwtAuthenticationToken);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        verify(cardService, times(1)).findAllByOwner(jwtAuthenticationToken);
    }

    private List<Card> createCardList() {
        // Create and return a list of Card objects
        List<Card> cardList = new ArrayList<>();
        cardList.add(CreditCard.builder().build());
        cardList.add(DebitCard.builder().build());
        return cardList;
    }

}