package com.yixuanliang.userapi.rest.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


class AuthResponseTest {

    @Test
    void testAuthResponseCreation() {
        String accessToken = "sampleAccessToken";

        AuthResponse authResponse = new AuthResponse(accessToken);

        assertEquals(accessToken, authResponse.accessToken());
    }
}