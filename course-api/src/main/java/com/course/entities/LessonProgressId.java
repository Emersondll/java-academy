package com.course.entities;

import java.io.Serializable;
import java.util.Objects;

public class LessonProgressId implements Serializable {

    private Long userId;
    private Long lessonId;

    public LessonProgressId() {
    }

    public LessonProgressId(Long userId, Long lessonId) {
        this.userId = userId;
        this.lessonId = lessonId;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LessonProgressId progressId)) {
            return false;
        }
        return Objects.equals(userId, progressId.userId) && Objects.equals(lessonId, progressId.lessonId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, lessonId);
    }
}
