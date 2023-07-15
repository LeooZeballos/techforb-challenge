package com.techforb.challenge.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Application configuration. This class is used to configure beans that are used in the application.
 *
 * @author Leonel Zeballos
 */
@Configuration
public class ApplicationConfiguration {

    /**
     * The public key used to decode JWT tokens.
     */
    @Value("${jwt.public.key}")
    private RSAPublicKey publicKey;

    /**
     * The private key used to encode JWT tokens.
     */
    @Value("${jwt.private.key}")
    private RSAPrivateKey privateKey;

    /**
     * Bean to decode JWT tokens.
     *
     * @return The JWT decoder.
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.publicKey).build();
    }

    /**
     * Bean to encode JWT tokens.
     *
     * @return The JWT encoder.
     */
    @Bean
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(this.publicKey).privateKey(this.privateKey).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

}
