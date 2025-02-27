package com.yixuanliang.userapi.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ErrorAttributesConfigTest {

    private ErrorAttributesConfig errorAttributesConfig;
    private WebRequest mockWebRequest;

    @BeforeEach
    void setUp() {
        errorAttributesConfig = new ErrorAttributesConfig();
        mockWebRequest = mock(WebRequest.class);
    }

    @Test
    void testErrorAttributesIncludesExpectedFields() {
        var errorAttributes = errorAttributesConfig.errorAttributes();

        ErrorAttributeOptions options = ErrorAttributeOptions.of(
                ErrorAttributeOptions.Include.EXCEPTION,
                ErrorAttributeOptions.Include.MESSAGE,
                ErrorAttributeOptions.Include.BINDING_ERRORS
        );

        Map<String, Object> attributes = errorAttributes.getErrorAttributes(mockWebRequest, options);

        assertNotNull(attributes);
        assertTrue(attributes.containsKey("message"), "Expected 'message' field in error attributes");
        assertTrue(attributes.containsKey("exception"), "Expected 'exception' field in error attributes");
        assertTrue(attributes.containsKey("errors") || attributes.containsKey("bindingErrors"), "Expected 'bindingErrors' field in error attributes");
    }
}