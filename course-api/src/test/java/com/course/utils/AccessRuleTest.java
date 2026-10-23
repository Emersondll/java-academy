package com.course.utils;

import com.course.enumerators.NavigationMode;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccessRuleTest {

    @Test
    void freeModeOpensAnyLesson() {
        assertTrue(AccessRule.isOpen(NavigationMode.FREE, 8, Set.of()));
    }

    @Test
    void firstTrailLessonIsOpen() {
        assertTrue(AccessRule.isOpen(NavigationMode.TRAIL, 1, Set.of()));
    }

    @Test
    void trailOpensALessonOnlyAfterThePreviousOnesAreComplete() {
        assertFalse(AccessRule.isOpen(NavigationMode.TRAIL, 3, Set.of(1)));
        assertTrue(AccessRule.isOpen(NavigationMode.TRAIL, 3, Set.of(1, 2)));
    }

    @Test
    void constructorIsPrivate() throws Exception {
        Constructor<AccessRule> constructor = AccessRule.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}
