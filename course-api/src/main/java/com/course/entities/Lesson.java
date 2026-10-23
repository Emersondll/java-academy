package com.course.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;

@Entity
@Table(name = "lesson")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "module_id", nullable = false)
    private LearningModule module;

    @Column(nullable = false)
    private int position;

    @Column(name = "global_order", nullable = false, unique = true)
    private int globalOrder;

    @Column(nullable = false, length = 240)
    private String title;

    @JdbcTypeCode(Types.LONGVARCHAR)
    @Column(nullable = false)
    private String summary;

    @Column(name = "video_url", length = 500)
    private String videoUrl;

    protected Lesson() {
    }

    public Long getId() {
        return id;
    }

    public LearningModule getModule() {
        return module;
    }

    public int getPosition() {
        return position;
    }

    public int getGlobalOrder() {
        return globalOrder;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getVideoUrl() {
        return videoUrl;
    }
}
