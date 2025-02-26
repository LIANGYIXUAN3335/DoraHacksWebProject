package com.yixuanliang.userapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(
        @Schema(example = "Johnny") @NotBlank String nickname,
        @Schema(example = "john@example.com") @Email @NotBlank String email,
        @Schema(example = "profile.jpg") String profilePicture) {
}