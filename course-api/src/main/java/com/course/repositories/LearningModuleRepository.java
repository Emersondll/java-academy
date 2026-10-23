package com.course.repositories;

import com.course.entities.LearningModule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LearningModuleRepository extends JpaRepository<LearningModule, Long> {

    List<LearningModule> findAllByOrderByPositionAsc();
}
