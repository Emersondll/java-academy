package com.course.dto;

import com.course.enumerators.NavigationMode;
import com.course.entities.UserAccount;

public record UserResponse(String name, String username, NavigationMode mode) {

    public static UserResponse from(UserAccount user) {
        return new UserResponse(user.getName(), user.getUsername(), user.getNavigationMode());
    }
}
