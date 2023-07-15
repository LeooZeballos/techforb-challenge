package com.techforb.challenge.auth;

import com.techforb.challenge.request.LoginRequest;
import com.techforb.challenge.request.RegisterRequest;
import com.techforb.challenge.response.TokenResponse;
import com.techforb.challenge.user.User;
import com.techforb.challenge.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
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
            UserDetails user = loadUserByUsername(loginRequest.dni());
            return generateToken(user);
        } catch (UsernameNotFoundException e) {
            throw new IllegalArgumentException("Invalid credentials for dni: " + loginRequest.dni());
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
        long expiry = 36000L; // 10 hours

        // Generate scope
        String scope = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        // Generate claims
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(user.getUsername())
                .claim("scope", scope)
                .build();

        // Generate token from claims
        return new TokenResponse(jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue());
    }

    /**
     * Load a user by username.
     *
     * @param dni The dni.
     * @return The user details.
     * @throws UsernameNotFoundException If the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String dni) throws UsernameNotFoundException {
        return userRepository.findByDni(dni)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Refresh a JWT token.
     *
     * @param refreshToken The refresh token.
     * @return The token response.
     */
    @Override
    public TokenResponse refreshToken(String refreshToken) {
        // TODO: Implement refresh token
        return null;
    }

}
