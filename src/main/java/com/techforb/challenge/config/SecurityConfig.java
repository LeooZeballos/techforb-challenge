package com.techforb.challenge.config;

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
public class SecurityConfig {

    /**
     * The API version.
     */
    @Value("${application.api.version}")
    private String apiVersion;

    /**
     * The base API path.
     */
    private final String baseApiPath = "/api/" + apiVersion;

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
                                baseApiPath +"/auth/**",       // Authentication endpoints
                                "/h2-console/**"                          // H2 console
                        ).permitAll()
                        .anyRequest().authenticated() // Any other request must be authenticated
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
