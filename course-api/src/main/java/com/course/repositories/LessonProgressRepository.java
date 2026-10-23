package com.course.repositories;

import com.course.entities.LessonProgress;
import com.course.entities.LessonProgressId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface LessonProgressRepository extends JpaRepository<LessonProgress, LessonProgressId> {

    @Query("select progress.lessonId from LessonProgress progress where progress.userId = :userId")
    Set<Long> findLessonIdsByUserId(Long userId);
}
