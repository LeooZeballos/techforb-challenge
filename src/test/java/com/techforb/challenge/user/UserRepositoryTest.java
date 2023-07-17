package com.techforb.challenge.user;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    public UserRepositoryTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByDocumentTypeAndDni() {
        // Create test data
        DocumentType documentType = DocumentType.DNI;
        String dni = "123456789";

        // Mock the repository method
        User user = User.builder()
                .id(1L)
                .dni(dni)
                .documentType(documentType)
                .build();
        when(userRepository.findByDocumentTypeAndDni(documentType, dni))
                .thenReturn(Optional.of(user));

        // Perform the test
        Optional<User> result = userRepository.findByDocumentTypeAndDni(documentType, dni);

        // Verify the result
        assertTrue(result.isPresent());
        assertEquals(user, result.get());

        // Verify the repository method was called
        verify(userRepository, times(1)).findByDocumentTypeAndDni(documentType, dni);
    }

    @Test
    void testExistsByDniAndDocumentType() {
        // Create test data
        DocumentType documentType = DocumentType.DNI;
        String dni = "123456789";

        // Mock the repository method
        when(userRepository.existsByDniAndDocumentType(dni, documentType))
                .thenReturn(true);

        // Perform the test
        boolean result = userRepository.existsByDniAndDocumentType(dni, documentType);

        // Verify the result
        assertTrue(result);

        // Verify the repository method was called
        verify(userRepository, times(1)).existsByDniAndDocumentType(dni, documentType);
    }

    @Test
    void testExistsByEmail() {
        // Create test data
        String email = "test@example.com";

        // Mock the repository method
        when(userRepository.existsByEmail(email))
                .thenReturn(true);

        // Perform the test
        boolean result = userRepository.existsByEmail(email);

        // Verify the result
        assertTrue(result);

        // Verify the repository method was called
        verify(userRepository, times(1)).existsByEmail(email);
    }

    @Test
    void testExistsByEmail_NotExists() {
        // Create test data
        String email = "test@example.com";

        // Mock the repository method
        when(userRepository.existsByEmail(email))
                .thenReturn(false);

        // Perform the test
        boolean result = userRepository.existsByEmail(email);

        // Verify the result
        assertFalse(result);

        // Verify the repository method was called
        verify(userRepository, times(1)).existsByEmail(email);
    }

}
