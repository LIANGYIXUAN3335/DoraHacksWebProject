package com.yixuanliang.userapi.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class CorsConfigTest {

    private CorsConfig corsConfig;

    @Value("${app.cors.allowed-origins}")
    private List<String> allowedOrigins;

    @BeforeEach
    void setUp() {
        corsConfig = new CorsConfig();
    }

    @Test
    void testCorsConfigurationSource() {
        CorsConfigurationSource source = corsConfig.corsConfigurationSource(allowedOrigins);
        assertNotNull(source);

        MockHttpServletRequest request = new MockHttpServletRequest();
        CorsConfiguration configuration = source.getCorsConfiguration(request);
        assertNotNull(configuration);
        assertTrue(configuration.getAllowedOrigins().containsAll(allowedOrigins));
        assertTrue(configuration.getAllowedMethods().contains("*"));
        assertTrue(configuration.getAllowedHeaders().contains("*"));
        assertTrue(configuration.getAllowCredentials());
    }

    @Bean
    public List<String> allowedOrigins() {
        return Arrays.asList("http://localhost:3000", "http://example.com");
    }
}