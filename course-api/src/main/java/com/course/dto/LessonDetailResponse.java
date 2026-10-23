package com.course.dto;

public record LessonDetailResponse(
        Long id,
        String title,
        String summary,
        String videoUrl,
        Long moduleId,
        String moduleTitle,
        int position,
        int globalOrder,
        int lessonCount,
        boolean completed,
        Long previousLessonId,
        Long nextLessonId
) {
}
