package com.course.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "lesson_progress")
@IdClass(LessonProgressId.class)
public class LessonProgress {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "lesson_id")
    private Long lessonId;

    @Column(name = "completed_at", nullable = false)
    private Instant completedAt;

    protected LessonProgress() {
    }

    public LessonProgress(Long userId, Long lessonId) {
        this.userId = userId;
        this.lessonId = lessonId;
        this.completedAt = Instant.now();
    }
}
