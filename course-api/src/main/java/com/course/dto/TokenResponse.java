package com.course.dto;

import com.course.enumerators.NavigationMode;

public record TokenResponse(String token, String name, String username, NavigationMode mode) {
}
