package com.yixuanliang.userapi.rest.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


class LoginRequestTest {

    @Test
    void testLoginRequestCreation() {
        String username = "user";
        String password = "password";

        LoginRequest loginRequest = new LoginRequest(username, password);

        assertEquals(username, loginRequest.username());
        assertEquals(password, loginRequest.password());
    }
}
