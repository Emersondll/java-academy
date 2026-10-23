package com.course.entities;

import com.course.enumerators.NavigationMode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EntityTest {

    @Test
    void userAccountStoresTheRegistrationData() {
        UserAccount empty = new UserAccount();
        assertNull(empty.getId());
        assertNull(empty.getName());

        UserAccount user = new UserAccount("Ana Lima", "ana.lima", "hash");
        assertEquals("Ana Lima", user.getName());
        assertEquals("ana.lima", user.getUsername());
        assertEquals("hash", user.getPasswordHash());
        assertEquals(NavigationMode.FREE, user.getNavigationMode());
        user.changeNavigationMode(NavigationMode.TRAIL);
        assertEquals(NavigationMode.TRAIL, user.getNavigationMode());
    }

    @Test
    void learningModuleExposesItsColumns() {
        LearningModule module = new LearningModule();
        assertNull(module.getId());
        assertEquals(0, module.getPosition());
        assertNull(module.getStage());
        assertNull(module.getTitle());
        assertNull(module.getSummary());
    }

    @Test
    void lessonExposesItsColumns() {
        Lesson lesson = new Lesson();
        assertNull(lesson.getId());
        assertNull(lesson.getModule());
        assertEquals(0, lesson.getPosition());
        assertEquals(0, lesson.getGlobalOrder());
        assertNull(lesson.getTitle());
        assertNull(lesson.getSummary());
        assertNull(lesson.getVideoUrl());
    }

    @Test
    void lessonProgressRecordsTheCompletion() {
        new LessonProgress();
        new LessonProgress(4L, 9L);
    }

    @Test
    void progressIdentityComparesBothKeys() {
        LessonProgressId empty = new LessonProgressId();
        assertEquals(empty, new LessonProgressId(null, null));
        LessonProgressId same = new LessonProgressId(1L, 2L);
        assertEquals(same, same);
        assertEquals(same, new LessonProgressId(1L, 2L));
        assertEquals(same.hashCode(), new LessonProgressId(1L, 2L).hashCode());
        assertNotEquals(same, new LessonProgressId(9L, 2L));
        assertNotEquals(same, new LessonProgressId(1L, 9L));
        assertNotEquals(same, new LessonProgressId(null, null));
        assertEquals(new LessonProgressId(null, null), new LessonProgressId(null, null));
        assertFalse(same.equals("1-2"));
        assertTrue(same.equals(new LessonProgressId(1L, 2L)));
    }
}
