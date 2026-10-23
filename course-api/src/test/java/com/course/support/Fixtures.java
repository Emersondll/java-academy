package com.course.support;

import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Constructor;

public final class Fixtures {

    private Fixtures() {
    }

    public static <T> T create(Class<T> type) {
        try {
            Constructor<T> constructor = type.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException(exception);
        }
    }

    public static void set(Object target, String field, Object value) {
        ReflectionTestUtils.setField(target, field, value);
    }
}
