package com.course.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "learning_module")
public class LearningModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private int position;

    @Column(nullable = false, length = 40)
    private String stage;

    @Column(nullable = false, length = 120)
    private String title;

    @Column(nullable = false, length = 400)
    private String summary;

    protected LearningModule() {
    }

    public Long getId() {
        return id;
    }

    public int getPosition() {
        return position;
    }

    public String getStage() {
        return stage;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }
}
