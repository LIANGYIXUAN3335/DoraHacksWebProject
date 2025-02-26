package com.yixuanliang.userapi.rest.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


class SignUpRequestTest {

    @Test
    void testSignUpRequestCreation() {
        String username = "user3";
        String password = "user3";
        String name = "User3";
        String email = "user3@mycompany.com";

        SignUpRequest signUpRequest = new SignUpRequest(username, password, name, email);

        assertEquals(username, signUpRequest.username());
        assertEquals(password, signUpRequest.password());
        assertEquals(name, signUpRequest.name());
        assertEquals(email, signUpRequest.email());
    }
}