package com.techforb.challenge.auth;

import com.techforb.challenge.request.LoginRequest;
import com.techforb.challenge.request.RegisterRequest;
import com.techforb.challenge.response.TokenResponse;
import com.techforb.challenge.user.DocumentType;
import com.techforb.challenge.user.Role;
import com.techforb.challenge.user.User;
import com.techforb.challenge.user.UserRepository;
import com.techforb.challenge.request.RefreshRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The user service implementation. It implements {@link IAuthService}.
 * It is annotated with {@link Service} to be automatically scanned and injected.
 * It is annotated with {@link RequiredArgsConstructor} to inject final fields.
 *
 * @see IAuthService
 * @author Leonel Zeballos
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    /**
     * The user repository.
     */
    private final UserRepository userRepository;

    /**
     * The JWT encoder.
     */
    private final JwtEncoder jwtEncoder;

    /**
     * The JWT decoder.
     */
    private final JwtDecoder jwtDecoder;

    /**
     * The JWT expiration time in seconds.
     */
    private final static long JWT_EXPIRATION_TIME = 60 * 60; // 1 hour


    /**
     * Register a new user. It also generates a JWT token.
     * DNI and DocumentType have to be unique.
     * Email also has to be unique.
     *
     * @param registerRequest The register request.
     */
    @Override
    public TokenResponse register(RegisterRequest registerRequest) {
        // Validate user
        // DNI and DocumentType have to be unique
        if (userRepository.existsByDniAndDocumentType(
                registerRequest.dni(),
                registerRequest.documentType()
        )) {
            throw new IllegalArgumentException("DNI already exists");
        }

        // Email also has to be unique
        if (userRepository.existsByEmail(registerRequest.email())) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Crate user
        User newUser = User.builder()
                .dni(registerRequest.dni())
                .password(registerRequest.password())
                .email(registerRequest.email())
                .firstName(registerRequest.firstName())
                .lastName(registerRequest.lastName())
                .documentType(registerRequest.documentType())
                .roles(List.of(Role.USER))
                .build();

        // Save user
        newUser = userRepository.saveAndFlush(newUser);

        // Generate token
        return generateToken(newUser);
    }

    /**
     * Login a user. It generates a JWT token.
     *
     * @param loginRequest The login request.
     * @return The token response.
     */
    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        // Validate user credentials
        try {
            UserDetails user = loadUserByUsername(loginRequest.documentType() + ":" + loginRequest.dni());
            return generateToken(user);
        } catch (UsernameNotFoundException e) {
            throw new IllegalArgumentException("Invalid credentials for " + loginRequest.documentType().toLowerCase() + ": " + loginRequest.dni());
        }
    }

    /**
     * Generate a JWT token.
     *
     * @param user The user.
     * @return The token response.
     */
    public TokenResponse generateToken(UserDetails user) {
        // Generate a JWT token
        Instant now = Instant.now();

        // Generate scope
        String scope = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        // Generate claims
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(JWT_EXPIRATION_TIME))
                .subject(user.getUsername())
                .claim("scope", scope)
                .build();

        // Generate token from claims
        return TokenResponse.builder()
                .token(jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue())
                .build();
    }

    /**
     * Refresh a JWT token.
     *
     * @param refreshToken The refresh token.
     * @return The token response.
     */
    @Override
    public TokenResponse refreshToken(RefreshRequest refreshToken) {
        // Decode the refresh token
        Jwt jwt = jwtDecoder.decode(refreshToken.token());

        // Get the claims
        Map<String, Object> claims = jwt.getClaims();

        // Get the user
        UserDetails user = loadUserByUsername(claims.get("sub").toString());

        // Validate the scope
        if (!user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "))
                .equals(claims.get("scope").toString())) {
            throw new IllegalArgumentException("Invalid scope");
        }

        // Validate the expiration time
        if (jwt.getExpiresAt() != null && Instant.now().isAfter(jwt.getExpiresAt())) {
            throw new IllegalArgumentException("Token expired");
        }

        // Generate a new token
        return generateToken(user);
    }

    /**
     * Load a user by username.
     *
     * @param username The username.
     * @return The user details.
     * @throws UsernameNotFoundException If the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Split username into documentType and dni (e.g., "DNI:123456789")
        String[] parts = username.split(":");

        // Validate format
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid documentTypePlusDni format");
        }

        // Find user by dni and documentType
        return userRepository.findByDocumentTypeAndDni(DocumentType.valueOf(parts[0]), parts[1])
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    }

    /**
     * Load a user by username.
     *
     * @param username The username.
     * @return The user details.
     * @throws UsernameNotFoundException If the user is not found.
     */
    public User loadUser(String username) throws UsernameNotFoundException {
        // Split username into documentType and dni (e.g., "DNI:123456789")
        String[] parts = username.split(":");

        // Validate format
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid documentTypePlusDni format");
        }

        // Find user by dni and documentType
        return userRepository.findByDocumentTypeAndDni(DocumentType.valueOf(parts[0]), parts[1])
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Get the current user from the JWT token.
     *
     * @param token The JWT token.
     * @return The user details.
     * @throws UsernameNotFoundException If the user is not found.
     */
    public User getCurrentUser(JwtAuthenticationToken token) throws UsernameNotFoundException {
        // Decode the token
        Jwt jwt = token.getToken();

        String username = jwt.getClaim("sub");

        // Get the user
        return loadUser(username);
    }

}
