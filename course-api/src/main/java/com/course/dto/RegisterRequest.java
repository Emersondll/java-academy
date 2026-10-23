package com.course.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 80, message = "Name must be between 2 and 80 characters")
        String name,
        @NotBlank(message = "Username is required")
        @Pattern(regexp = "^[a-zA-Z0-9._-]{3,40}$", message = "Username must be 3 to 40 characters: letters, digits, dot, _ or -")
        String username,
        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 80, message = "Password must be between 8 and 80 characters")
        String password
) {
}
