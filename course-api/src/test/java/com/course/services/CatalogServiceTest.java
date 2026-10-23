package com.course.services;

import com.course.dto.DashboardResponse;
import com.course.dto.LessonDetailResponse;
import com.course.dto.ModuleDetailResponse;
import com.course.entities.LearningModule;
import com.course.entities.Lesson;
import com.course.entities.UserAccount;
import com.course.enumerators.NavigationMode;
import com.course.exception.BusinessRuleException;
import com.course.exception.LessonLockedException;
import com.course.repositories.LearningModuleRepository;
import com.course.repositories.LessonProgressRepository;
import com.course.repositories.LessonRepository;
import com.course.support.Fixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CatalogServiceTest {

    @Mock
    private AuthService authService;
    @Mock
    private LearningModuleRepository modules;
    @Mock
    private LessonRepository lessons;
    @Mock
    private LessonProgressRepository progress;

    private CatalogService catalogService;
    private UserAccount user;

    @BeforeEach
    void setUp() {
        catalogService = new CatalogService(authService, modules, lessons, progress);
        user = new UserAccount("Ana Lima", "ana.lima", "hash");
        Fixtures.set(user, "id", 7L);
    }

    @Test
    void dashboardCountsProgressAndSkipsAnEmptyModule() {
        user.changeNavigationMode(NavigationMode.TRAIL);
        when(authService.load("ana.lima")).thenReturn(user);
        LearningModule language = module(1L, 1, "Beginner", "Language", "Types");
        LearningModule empty = module(2L, 2, "Beginner", "Objects", "Classes");
        Lesson first = lesson(10L, language, 1, 1, "JDK");
        Lesson second = lesson(11L, language, 2, 2, "Types");
        when(modules.findAllByOrderByPositionAsc()).thenReturn(List.of(language, empty));
        when(lessons.findAllWithModule()).thenReturn(List.of(first, second));
        when(progress.findLessonIdsByUserId(7L)).thenReturn(Set.of(10L));

        DashboardResponse dashboard = catalogService.dashboard("ana.lima");

        assertEquals("Ana Lima", dashboard.name());
        assertEquals("ana.lima", dashboard.username());
        assertEquals(NavigationMode.TRAIL, dashboard.mode());
        assertEquals(2, dashboard.lessonCount());
        assertEquals(1, dashboard.completedLessonCount());
        assertEquals(11L, dashboard.nextLessonId());
        DashboardResponse.ModuleCard languageCard = dashboard.modules().get(0);
        assertEquals(1L, languageCard.id());
        assertEquals(1, languageCard.position());
        assertEquals("Beginner", languageCard.stage());
        assertEquals("Language", languageCard.title());
        assertEquals("Types", languageCard.summary());
        assertEquals(2, languageCard.lessonCount());
        assertEquals(1, languageCard.completedLessonCount());
        assertEquals(0, dashboard.modules().get(1).lessonCount());
    }

    @Test
    void dashboardReturnsNoNextLessonWhenEverythingIsComplete() {
        when(authService.load("ana.lima")).thenReturn(user);
        LearningModule language = module(1L, 1, "Beginner", "Language", "Types");
        Lesson first = lesson(10L, language, 1, 1, "JDK");
        when(modules.findAllByOrderByPositionAsc()).thenReturn(List.of(language));
        when(lessons.findAllWithModule()).thenReturn(List.of(first));
        when(progress.findLessonIdsByUserId(7L)).thenReturn(Set.of(10L));

        assertNull(catalogService.dashboard("ana.lima").nextLessonId());
    }

    @Test
    void nextOpenLessonSkipsALockedGapBeforeTheOpenOne() {
        user.changeNavigationMode(NavigationMode.TRAIL);
        when(authService.load("ana.lima")).thenReturn(user);
        LearningModule language = module(1L, 1, "Beginner", "Language", "Types");
        Lesson later = lesson(11L, language, 2, 2, "Types");
        Lesson earlier = lesson(10L, language, 1, 1, "JDK");
        when(modules.findAllByOrderByPositionAsc()).thenReturn(List.of(language));
        when(lessons.findAllWithModule()).thenReturn(List.of(later, earlier));
        when(progress.findLessonIdsByUserId(7L)).thenReturn(Set.of());

        assertEquals(10L, catalogService.dashboard("ana.lima").nextLessonId());
    }

    @Test
    void moduleListsOpenAndLockedLessons() {
        user.changeNavigationMode(NavigationMode.TRAIL);
        when(authService.load("ana.lima")).thenReturn(user);
        LearningModule language = module(1L, 1, "Beginner", "Language", "Types");
        Lesson first = lesson(10L, language, 1, 1, "JDK");
        Lesson second = lesson(11L, language, 2, 2, "Types");
        when(modules.findById(1L)).thenReturn(Optional.of(language));
        when(lessons.findAllWithModule()).thenReturn(List.of(first, second));
        when(lessons.findByModuleId(1L)).thenReturn(List.of(first, second));
        when(progress.findLessonIdsByUserId(7L)).thenReturn(Set.of(10L));

        ModuleDetailResponse detail = catalogService.module("ana.lima", 1L);

        assertEquals(1L, detail.id());
        assertEquals(1, detail.position());
        assertEquals("Beginner", detail.stage());
        assertEquals("Language", detail.title());
        assertEquals("Types", detail.summary());
        assertEquals(10L, detail.lessons().get(0).id());
        assertEquals(1, detail.lessons().get(0).position());
        assertEquals("JDK", detail.lessons().get(0).title());
        assertTrue(detail.lessons().get(0).completed());
        assertTrue(detail.lessons().get(0).open());
        assertTrue(detail.lessons().get(1).open());
    }

    @Test
    void missingModuleIsRejected() {
        when(authService.load("ana.lima")).thenReturn(user);
        when(modules.findById(9L)).thenReturn(Optional.empty());
        assertThrows(BusinessRuleException.class, () -> catalogService.module("ana.lima", 9L));
    }

    @Test
    void lessonDetailLinksThePreviousAndNextLessons() {
        when(authService.load("ana.lima")).thenReturn(user);
        LearningModule language = module(1L, 1, "Beginner", "Language", "Types");
        Lesson first = lesson(10L, language, 1, 1, "JDK");
        Lesson middle = lesson(11L, language, 2, 2, "Types");
        Lesson last = lesson(12L, language, 3, 3, "Text");
        when(lessons.findAllWithModule()).thenReturn(List.of(first, middle, last));
        when(progress.findLessonIdsByUserId(7L)).thenReturn(Set.of());

        LessonDetailResponse opened = catalogService.lesson("ana.lima", 11L);

        assertEquals(11L, opened.id());
        assertEquals("Types", opened.title());
        assertEquals("summary", opened.summary());
        assertEquals("https://example.test/video", opened.videoUrl());
        assertEquals(1L, opened.moduleId());
        assertEquals("Language", opened.moduleTitle());
        assertEquals(2, opened.position());
        assertEquals(2, opened.globalOrder());
        assertEquals(3, opened.lessonCount());
        assertEquals(false, opened.completed());
        assertEquals(10L, opened.previousLessonId());
        assertEquals(12L, opened.nextLessonId());
    }

    @Test
    void firstAndLastLessonsHaveOneNeighbor() {
        when(authService.load("ana.lima")).thenReturn(user);
        LearningModule language = module(1L, 1, "Beginner", "Language", "Types");
        Lesson only = lesson(10L, language, 1, 1, "JDK");
        when(lessons.findAllWithModule()).thenReturn(List.of(only));
        when(progress.findLessonIdsByUserId(7L)).thenReturn(Set.of());

        LessonDetailResponse opened = catalogService.lesson("ana.lima", 10L);

        assertNull(opened.previousLessonId());
        assertNull(opened.nextLessonId());
    }

    @Test
    void missingLessonIsRejected() {
        when(authService.load("ana.lima")).thenReturn(user);
        LearningModule language = module(1L, 1, "Beginner", "Language", "Types");
        when(lessons.findAllWithModule()).thenReturn(List.of(lesson(10L, language, 1, 1, "JDK")));
        assertThrows(BusinessRuleException.class, () -> catalogService.lesson("ana.lima", 99L));
    }

    @Test
    void trailRejectsALockedLesson() {
        user.changeNavigationMode(NavigationMode.TRAIL);
        when(authService.load("ana.lima")).thenReturn(user);
        LearningModule language = module(1L, 1, "Beginner", "Language", "Types");
        Lesson first = lesson(10L, language, 1, 1, "JDK");
        Lesson second = lesson(11L, language, 2, 2, "Types");
        when(lessons.findAllWithModule()).thenReturn(List.of(first, second));
        when(progress.findLessonIdsByUserId(7L)).thenReturn(Set.of());

        LessonLockedException error = assertThrows(LessonLockedException.class, () -> catalogService.lesson("ana.lima", 11L));
        assertEquals(10L, error.getOpenLessonId());
    }

    @Test
    void completeSavesProgressOnce() {
        when(authService.load("ana.lima")).thenReturn(user);
        LearningModule language = module(1L, 1, "Beginner", "Language", "Types");
        Lesson first = lesson(10L, language, 1, 1, "JDK");
        Lesson last = lesson(12L, language, 2, 2, "Text");
        when(lessons.findAllWithModule()).thenReturn(List.of(first, last));
        when(progress.findLessonIdsByUserId(7L)).thenReturn(Set.of());

        LessonDetailResponse completed = catalogService.complete("ana.lima", 10L);

        assertTrue(completed.completed());
        assertEquals(12L, completed.nextLessonId());
        verify(progress).save(any());
    }

    @Test
    void completeIsIdempotent() {
        when(authService.load("ana.lima")).thenReturn(user);
        LearningModule language = module(1L, 1, "Beginner", "Language", "Types");
        Lesson first = lesson(10L, language, 1, 1, "JDK");
        when(lessons.findAllWithModule()).thenReturn(List.of(first));
        when(progress.findLessonIdsByUserId(7L)).thenReturn(Set.of(10L));

        assertTrue(catalogService.complete("ana.lima", 10L).completed());
        verify(progress, never()).save(any());
    }

    private LearningModule module(Long id, int position, String stage, String title, String summary) {
        LearningModule module = Fixtures.create(LearningModule.class);
        Fixtures.set(module, "id", id);
        Fixtures.set(module, "position", position);
        Fixtures.set(module, "stage", stage);
        Fixtures.set(module, "title", title);
        Fixtures.set(module, "summary", summary);
        return module;
    }

    private Lesson lesson(Long id, LearningModule module, int position, int globalOrder, String title) {
        Lesson lesson = Fixtures.create(Lesson.class);
        Fixtures.set(lesson, "id", id);
        Fixtures.set(lesson, "module", module);
        Fixtures.set(lesson, "position", position);
        Fixtures.set(lesson, "globalOrder", globalOrder);
        Fixtures.set(lesson, "title", title);
        Fixtures.set(lesson, "summary", "summary");
        Fixtures.set(lesson, "videoUrl", "https://example.test/video");
        return lesson;
    }
}
