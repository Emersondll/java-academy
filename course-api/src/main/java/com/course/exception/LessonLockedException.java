package com.course.exception;

public class LessonLockedException extends RuntimeException {

    private final Long openLessonId;

    public LessonLockedException(Long openLessonId) {
        super("Finish the previous lesson before opening this one");
        this.openLessonId = openLessonId;
    }

    public Long getOpenLessonId() {
        return openLessonId;
    }
}
