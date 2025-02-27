package com.yixuanliang.userapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class SwaggerConfigTest {

    private SwaggerConfig swaggerConfig;

    @BeforeEach
    void setUp() {
        swaggerConfig = new SwaggerConfig();
        ReflectionTestUtils.setField(swaggerConfig, "applicationName", "Test Application");
    }

    @Test
    void testCustomOpenAPI() {
        OpenAPI openAPI = swaggerConfig.customOpenAPI();

        assertNotNull(openAPI);

        Info info = openAPI.getInfo();
        assertNotNull(info);
        assertEquals("Test Application", info.getTitle(), "Expected application title to be set correctly");

        SecurityScheme securityScheme = openAPI.getComponents().getSecuritySchemes()
                .get(SwaggerConfig.BEARER_KEY_SECURITY_SCHEME);
        assertNotNull(securityScheme, "Expected security scheme to be defined");
        assertEquals(SecurityScheme.Type.HTTP, securityScheme.getType(), "Expected security scheme type to be HTTP");
        assertEquals("bearer", securityScheme.getScheme(), "Expected security scheme to be bearer");
        assertEquals("JWT", securityScheme.getBearerFormat(), "Expected bearer format to be JWT");
    }
}