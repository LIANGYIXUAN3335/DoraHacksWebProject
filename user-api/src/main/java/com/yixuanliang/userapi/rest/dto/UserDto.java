package com.yixuanliang.userapi.rest.dto;


import com.yixuanliang.userapi.user.User;

public record UserDto(Long id, String username, String name, String email, String role) {

    public static UserDto from(User user) {

        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getNickname(),
                user.getEmail(),
                user.getRole()
        );
    }
}