package com.yixuanliang.userapi.rest.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.yixuanliang.userapi.user.User;

class UserDtoTest {

    @Test
    void testUserDtoCreation() {
        Long id = 1L;
        String username = "user";
        String name = "User";
        String email = "user@example.com";
        String role = "ROLE_USER";

        UserDto userDto = new UserDto(id, username, name, email, role);

        assertEquals(id, userDto.id());
        assertEquals(username, userDto.username());
        assertEquals(name, userDto.name());
        assertEquals(email, userDto.email());
        assertEquals(role, userDto.role());
    }

    @Test
    void testUserDtoFromUser() {
        Long id = 1L;
        String username = "user";
        String nickname = "User";
        String email = "user@example.com";
        String role = "ROLE_USER";

        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setNickname(nickname);
        user.setEmail(email);
        user.setRole(role);

        UserDto userDto = UserDto.from(user);

        assertEquals(id, userDto.id());
        assertEquals(username, userDto.username());
        assertEquals(nickname, userDto.name());
        assertEquals(email, userDto.email());
        assertEquals(role, userDto.role());
    }
}