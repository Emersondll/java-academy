package com.course.dto;

import com.course.enumerators.NavigationMode;

import java.util.List;

public record DashboardResponse(
        String name,
        String username,
        NavigationMode mode,
        int lessonCount,
        int completedLessonCount,
        Long nextLessonId,
        List<ModuleCard> modules
) {
    public record ModuleCard(
            Long id,
            int position,
            String stage,
            String title,
            String summary,
            int lessonCount,
            int completedLessonCount
    ) {
    }
}
