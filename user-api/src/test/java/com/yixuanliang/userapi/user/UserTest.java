package com.yixuanliang.userapi.user;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserConstructorAndGetters() {
        String username = "user";
        String password = "password";
        String nickname = "User";
        String email = "user@example.com";
        String role = "ROLE_USER";
        User user = new User(username, password, nickname, email, role);

        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(nickname, user.getNickname());
        assertEquals(email, user.getEmail());
        assertEquals(role, user.getRole());
        assertNull(user.getProfilePicture()); 
    }

    @Test
    void testUserSetters() {
        User user = new User();
        user.setUsername("user");
        user.setPassword("password");
        user.setNickname("User");
        user.setEmail("user@example.com");
        user.setRole("ROLE_USER");
        user.setProfilePicture("profile.jpg");

        assertEquals("user", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals("User", user.getNickname());
        assertEquals("user@example.com", user.getEmail());
        assertEquals("ROLE_USER", user.getRole());
        assertEquals("profile.jpg", user.getProfilePicture());
    }
}