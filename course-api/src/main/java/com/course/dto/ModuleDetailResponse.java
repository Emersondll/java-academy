package com.course.dto;

import java.util.List;

public record ModuleDetailResponse(
        Long id,
        int position,
        String stage,
        String title,
        String summary,
        List<LessonItem> lessons
) {
    public record LessonItem(Long id, int position, String title, boolean completed, boolean open) {
    }
}
