package com.course.dto;

import com.course.enumerators.NavigationMode;
import jakarta.validation.constraints.NotNull;

public record NavigationModeRequest(@NotNull(message = "Navigation mode is required") NavigationMode mode) {
}
