package com.techforb.challenge.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration for the application.
 *
 * @author Leonel Zeballos
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * The application API version.
     */
    @Value("${application.api.version}")
    private String version;

    /**
     * The application API base path.
     */
    @Value("${application.api.base-path}")
    private String basePath;

    /**
     * The base API path.
     */
    private String apiPath;

    @PostConstruct
    public void init() {
        apiPath = basePath + "/" + version;
    }

    /**
     * Configures the security filter chain.
     *
     * @param http the {@link HttpSecurity} to configure
     * @return the {@link SecurityFilterChain}
     * @throws Exception if an error occurs
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                        .requestMatchers(
                                apiPath +"/auth/**",       // Authentication endpoints
                                "/h2-console/**"           // H2 console
                        ).permitAll()
                        .anyRequest().authenticated()        // Any other request must be authenticated
                )
                .httpBasic(Customizer.withDefaults())
                .oauth2ResourceServer(jwt -> jwt.jwt(Customizer.withDefaults()))
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling((exceptions) -> exceptions
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                )
                .build();
    }

}
