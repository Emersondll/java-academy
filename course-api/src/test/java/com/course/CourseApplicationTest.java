package com.course;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

import static org.mockito.Mockito.mockStatic;

class CourseApplicationTest {

    @Test
    void mainStartsTheApplication() {
        try (MockedStatic<SpringApplication> spring = mockStatic(SpringApplication.class)) {
            String[] args = {"--spring.main.banner-mode=off"};
            CourseApplication.main(args);
            spring.verify(() -> SpringApplication.run(CourseApplication.class, args));
        }
    }
}
