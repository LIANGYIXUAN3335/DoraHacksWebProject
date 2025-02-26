package com.yixuanliang.userapi.rest.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;


class UpdateUserRequestTest {

    @Test
    void testUpdateUserRequestCreation() {
        String nickname = "Johnny";
        String email = "john@example.com";
        String profilePicture = "profile.jpg";

        UpdateUserRequest updateUserRequest = new UpdateUserRequest(nickname, email, profilePicture);

        assertEquals(nickname, updateUserRequest.nickname());
        assertEquals(email, updateUserRequest.email());
        assertEquals(profilePicture, updateUserRequest.profilePicture());
    }

    @Test
    void testUpdateUserRequestCreationWithoutProfilePicture() {
        String nickname = "Johnny";
        String email = "john@example.com";

        UpdateUserRequest updateUserRequest = new UpdateUserRequest(nickname, email, null);

        assertEquals(nickname, updateUserRequest.nickname());
        assertEquals(email, updateUserRequest.email());
        assertNull(updateUserRequest.profilePicture());
    }
}