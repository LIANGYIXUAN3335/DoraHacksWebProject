package com.yixuanliang.userapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TokenProviderTest {

    @Mock
    private Authentication authentication;

    @Mock
    private CustomUserDetails customUserDetails;

    private TokenProvider tokenProvider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tokenProvider = new TokenProvider();
        tokenProvider.jwtSecret = "mySecretKey12345678901234567890123456789012"; // 32-byte secret key
        tokenProvider.jwtExpirationMinutes = 60L;
    }


    @Test
    void testValidateTokenAndGetJws_ValidToken() {
        String token = generateTestToken();

        Optional<Jws<Claims>> jwsOptional = tokenProvider.validateTokenAndGetJws(token);

        assertTrue(jwsOptional.isPresent());
        assertEquals("user", jwsOptional.get().getBody().getSubject());
    }

    @Test
    void testValidateTokenAndGetJws_InvalidToken() {
        String token = "invalidToken";

        Optional<Jws<Claims>> jwsOptional = tokenProvider.validateTokenAndGetJws(token);

        assertFalse(jwsOptional.isPresent());
    }

    @SuppressWarnings("deprecation")
    private String generateTestToken() {
        byte[] signingKey = tokenProvider.jwtSecret.getBytes();

        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKey))
                .setHeaderParam("typ", TokenProvider.TOKEN_TYPE)
                .setIssuer(TokenProvider.TOKEN_ISSUER)
                .setAudience(TokenProvider.TOKEN_AUDIENCE)
                .setSubject("user")
                .setExpiration(new Date(System.currentTimeMillis() + 60000))
                .claim("rol", List.of("ROLE_USER"))
                .claim("name", "User")
                .claim("preferred_username", "user")
                .claim("email", "user@example.com")
                .compact();
    }
}