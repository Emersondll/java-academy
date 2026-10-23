package com.course.dto;

import com.course.entities.UserAccount;
import com.course.enumerators.NavigationMode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ResponseContractTest {

    @Test
    void recordsExposeEveryComponent() {
        TokenResponse token = new TokenResponse("token", "Ana Lima", "ana.lima", NavigationMode.FREE);
        assertEquals(token, new TokenResponse("token", "Ana Lima", "ana.lima", NavigationMode.FREE));
        assertEquals(token.hashCode(), new TokenResponse("token", "Ana Lima", "ana.lima", NavigationMode.FREE).hashCode());
        assertNotEquals(token, new TokenResponse("other", "Ana Lima", "ana.lima", NavigationMode.FREE));
        assertEquals("token", token.token());
        token.toString();

        UserResponse user = UserResponse.from(new UserAccount("Ana Lima", "ana.lima", "hash"));
        assertEquals(new UserResponse("Ana Lima", "ana.lima", NavigationMode.FREE), user);
        assertEquals(user.hashCode(), new UserResponse("Ana Lima", "ana.lima", NavigationMode.FREE).hashCode());
        user.toString();

        ErrorResponse error = new ErrorResponse("missing");
        assertEquals(new ErrorResponse("missing", null), error);
        assertNotEquals(error, new ErrorResponse("missing", 2L));
        error.hashCode();
        error.toString();

        DashboardResponse.ModuleCard card = new DashboardResponse.ModuleCard(1L, 1, "Beginner", "Language", "Types", 2, 1);
        DashboardResponse dashboard = new DashboardResponse("Ana Lima", "ana.lima", NavigationMode.FREE, 2, 1, null, List.of(card));
        assertEquals(dashboard, new DashboardResponse("Ana Lima", "ana.lima", NavigationMode.FREE, 2, 1, null, List.of(card)));
        dashboard.hashCode();
        dashboard.toString();
        card.hashCode();
        card.toString();

        ModuleDetailResponse.LessonItem item = new ModuleDetailResponse.LessonItem(3L, 1, "JDK", false, true);
        ModuleDetailResponse module = new ModuleDetailResponse(1L, 1, "Beginner", "Language", "Types", List.of(item));
        assertEquals(module, new ModuleDetailResponse(1L, 1, "Beginner", "Language", "Types", List.of(item)));
        module.hashCode();
        module.toString();
        assertNotEquals(item, new ModuleDetailResponse.LessonItem(4L, 1, "JDK", false, true));
        item.hashCode();
        item.toString();

        LessonDetailResponse lesson = new LessonDetailResponse(3L, "JDK", "summary", null, 1L, "Language", 1, 1, 2, false, null, 4L);
        assertEquals(lesson, new LessonDetailResponse(3L, "JDK", "summary", null, 1L, "Language", 1, 1, 2, false, null, 4L));
        assertNotEquals(lesson, new LessonDetailResponse(4L, "JDK", "summary", null, 1L, "Language", 1, 1, 2, false, null, 4L));
        lesson.hashCode();
        lesson.toString();

        RegisterRequest register = new RegisterRequest("Ana Lima", "ana.lima", "senha1234");
        assertEquals(register, new RegisterRequest("Ana Lima", "ana.lima", "senha1234"));
        assertEquals("Ana Lima", register.name());
        assertEquals("ana.lima", register.username());
        assertEquals("senha1234", register.password());
        register.hashCode();
        register.toString();

        LoginRequest login = new LoginRequest("ana.lima", "senha1234");
        assertEquals(login, new LoginRequest("ana.lima", "senha1234"));
        login.hashCode();
        login.toString();

        NavigationModeRequest mode = new NavigationModeRequest(NavigationMode.TRAIL);
        assertEquals(NavigationMode.TRAIL, mode.mode());
        assertEquals(mode, new NavigationModeRequest(NavigationMode.TRAIL));
        assertNotEquals(mode, new NavigationModeRequest(NavigationMode.FREE));
        mode.hashCode();
        mode.toString();
    }
}
