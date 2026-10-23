package com.course.repositories;

import com.course.entities.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query("select lesson from Lesson lesson join fetch lesson.module order by lesson.globalOrder")
    List<Lesson> findAllWithModule();

    @Query("select lesson from Lesson lesson join fetch lesson.module where lesson.module.id = :moduleId order by lesson.position")
    List<Lesson> findByModuleId(Long moduleId);
}
