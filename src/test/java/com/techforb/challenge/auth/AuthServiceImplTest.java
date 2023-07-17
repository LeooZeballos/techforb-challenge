package com.techforb.challenge.auth;

import com.techforb.challenge.request.RegisterRequest;
import com.techforb.challenge.user.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.Authentication;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testRegister_DuplicateDni() {
        // Create test data
        RegisterRequest registerRequest = new RegisterRequest(
                "123456789",
                "password",
                "test@example.com",
                "John",
                "Doe",
                DocumentType.DNI
        );

        // Mock the repository methods
        when(userRepository.existsByDniAndDocumentType(registerRequest.dni(), registerRequest.documentType()))
                .thenReturn(true);

        // Perform the test and verify the exception
        assertThrows(IllegalArgumentException.class, () -> authService.register(registerRequest));

        // Verify the repository methods were called
        verify(userRepository, times(1)).existsByDniAndDocumentType(registerRequest.dni(), registerRequest.documentType());
        verify(userRepository, never()).existsByEmail(registerRequest.email());
        verify(userRepository, never()).saveAndFlush(any(User.class));
    }

    @Test
    void testRegister_DuplicateEmail() {
        // Create test data
        RegisterRequest registerRequest = new RegisterRequest(
                "123456789",
                "password",
                "test@example.com",
                "John",
                "Doe",
                DocumentType.DNI
        );

        // Mock the repository methods
        when(userRepository.existsByDniAndDocumentType(registerRequest.dni(), registerRequest.documentType()))
                .thenReturn(false);
        when(userRepository.existsByEmail(registerRequest.email())).thenReturn(true);

        // Perform the test and verify the exception
        assertThrows(IllegalArgumentException.class, () -> authService.register(registerRequest));

        // Verify the repository methods were called
        verify(userRepository, times(1)).existsByDniAndDocumentType(registerRequest.dni(), registerRequest.documentType());
        verify(userRepository, times(1)).existsByEmail(registerRequest.email());
        verify(userRepository, never()).saveAndFlush(any(User.class));
    }

    @Test
    void testLoadUserByUsername() {
        // Create test data
        User user = User.builder().documentType(DocumentType.DNI).dni("123456789").build();
        when(userRepository.findByDocumentTypeAndDni(DocumentType.DNI, "123456789")).thenReturn(Optional.of(user));

        // Perform the test
        UserDetails result = authService.loadUserByUsername("DNI:123456789");

        // Verify the result
        assertNotNull(result);
        assertEquals("DNI:123456789", result.getUsername());

        // Verify the repository method was called
        verify(userRepository, times(1)).findByDocumentTypeAndDni(DocumentType.DNI, "123456789");
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Mock the repository method
        when(userRepository.findByDocumentTypeAndDni(DocumentType.DNI, "123456789")).thenReturn(Optional.empty());

        // Perform the test and verify the exception
        assertThrows(UsernameNotFoundException.class, () -> authService.loadUserByUsername("DNI:123456789"));

        // Verify the repository method was called
        verify(userRepository, times(1)).findByDocumentTypeAndDni(DocumentType.DNI, "123456789");
    }

    @Test
    void testLoadUser() {
        // Create test data
        User user = User.builder().documentType(DocumentType.DNI).dni("123456789").build();
        when(userRepository.findByDocumentTypeAndDni(DocumentType.DNI, "123456789")).thenReturn(Optional.of(user));

        // Perform the test
        User result = authService.loadUser("DNI:123456789");

        // Verify the result
        assertNotNull(result);
        assertEquals("DNI:123456789", result.getUsername());

        // Verify the repository method was called
        verify(userRepository, times(1)).findByDocumentTypeAndDni(DocumentType.DNI, "123456789");
    }

    @Test
    void testLoadUser_UserNotFound() {
        // Mock the repository method
        when(userRepository.findByDocumentTypeAndDni(DocumentType.DNI, "123456789")).thenReturn(Optional.empty());

        // Perform the test and verify the exception
        assertThrows(UsernameNotFoundException.class, () -> authService.loadUser("DNI:123456789"));

        // Verify the repository method was called
        verify(userRepository, times(1)).findByDocumentTypeAndDni(DocumentType.DNI, "123456789");
    }

}
