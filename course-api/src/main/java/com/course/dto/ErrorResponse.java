package com.course.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(String message, Long openLessonId) {

    public ErrorResponse(String message) {
        this(message, null);
    }
}
