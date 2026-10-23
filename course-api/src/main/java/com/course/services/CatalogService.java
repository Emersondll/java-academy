package com.course.services;

import com.course.dto.DashboardResponse;
import com.course.dto.LessonDetailResponse;
import com.course.dto.ModuleDetailResponse;
import com.course.entities.LearningModule;
import com.course.entities.Lesson;
import com.course.entities.LessonProgress;
import com.course.entities.UserAccount;
import com.course.enumerators.NavigationMode;
import com.course.exception.BusinessRuleException;
import com.course.exception.LessonLockedException;
import com.course.repositories.LearningModuleRepository;
import com.course.repositories.LessonProgressRepository;
import com.course.repositories.LessonRepository;
import com.course.utils.AccessRule;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CatalogService {

    private final AuthService authService;
    private final LearningModuleRepository modules;
    private final LessonRepository lessons;
    private final LessonProgressRepository progress;

    public CatalogService(
            AuthService authService,
            LearningModuleRepository modules,
            LessonRepository lessons,
            LessonProgressRepository progress) {
        this.authService = authService;
        this.modules = modules;
        this.lessons = lessons;
        this.progress = progress;
    }

    @Transactional(readOnly = true)
    public DashboardResponse dashboard(String username) {
        UserAccount user = authService.load(username);
        List<Lesson> allLessons = lessons.findAllWithModule();
        Set<Long> completedIds = progress.findLessonIdsByUserId(user.getId());
        Set<Integer> completedOrders = completedOrders(allLessons, completedIds);

        List<DashboardResponse.ModuleCard> cards = modules.findAllByOrderByPositionAsc().stream()
                .map(module -> {
                    List<Lesson> moduleLessons = allLessons.stream()
                            .filter(lesson -> lesson.getModule().getId().equals(module.getId()))
                            .toList();
                    int completed = (int) moduleLessons.stream().filter(lesson -> completedIds.contains(lesson.getId())).count();
                    return new DashboardResponse.ModuleCard(
                            module.getId(),
                            module.getPosition(),
                            module.getStage(),
                            module.getTitle(),
                            module.getSummary(),
                            moduleLessons.size(),
                            completed);
                })
                .toList();

        return new DashboardResponse(
                user.getName(),
                user.getUsername(),
                user.getNavigationMode(),
                allLessons.size(),
                completedIds.size(),
                nextOpenLessonId(allLessons, user.getNavigationMode(), completedOrders, completedIds),
                cards);
    }

    @Transactional(readOnly = true)
    public ModuleDetailResponse module(String username, Long moduleId) {
        UserAccount user = authService.load(username);
        LearningModule module = modules.findById(moduleId)
                .orElseThrow(() -> new BusinessRuleException(HttpStatus.NOT_FOUND, "Module not found"));
        List<Lesson> allLessons = lessons.findAllWithModule();
        Set<Long> completedIds = progress.findLessonIdsByUserId(user.getId());
        Set<Integer> completedOrders = completedOrders(allLessons, completedIds);
        List<ModuleDetailResponse.LessonItem> items = lessons.findByModuleId(moduleId).stream()
                .map(lesson -> new ModuleDetailResponse.LessonItem(
                        lesson.getId(),
                        lesson.getPosition(),
                        lesson.getTitle(),
                        completedIds.contains(lesson.getId()),
                        AccessRule.isOpen(user.getNavigationMode(), lesson.getGlobalOrder(), completedOrders)))
                .toList();
        return new ModuleDetailResponse(
                module.getId(),
                module.getPosition(),
                module.getStage(),
                module.getTitle(),
                module.getSummary(),
                items);
    }

    @Transactional(readOnly = true)
    public LessonDetailResponse lesson(String username, Long lessonId) {
        UserAccount user = authService.load(username);
        List<Lesson> allLessons = lessons.findAllWithModule();
        Lesson lesson = find(allLessons, lessonId);
        Set<Long> completedIds = progress.findLessonIdsByUserId(user.getId());
        Set<Integer> completedOrders = completedOrders(allLessons, completedIds);
        requireOpen(user.getNavigationMode(), lesson, completedOrders, allLessons, completedIds);
        return detail(lesson, allLessons, completedIds.contains(lesson.getId()));
    }

    @Transactional
    public LessonDetailResponse complete(String username, Long lessonId) {
        UserAccount user = authService.load(username);
        List<Lesson> allLessons = lessons.findAllWithModule();
        Lesson lesson = find(allLessons, lessonId);
        Set<Long> completedIds = new HashSet<>(progress.findLessonIdsByUserId(user.getId()));
        Set<Integer> completedOrders = completedOrders(allLessons, completedIds);
        requireOpen(user.getNavigationMode(), lesson, completedOrders, allLessons, completedIds);
        if (!completedIds.contains(lesson.getId())) {
            progress.save(new LessonProgress(user.getId(), lesson.getId()));
            completedIds.add(lesson.getId());
        }
        return detail(lesson, allLessons, true);
    }

    private LessonDetailResponse detail(Lesson lesson, List<Lesson> allLessons, boolean completed) {
        int index = allLessons.indexOf(lesson);
        Long previousId = index > 0 ? allLessons.get(index - 1).getId() : null;
        Long nextId = index < allLessons.size() - 1 ? allLessons.get(index + 1).getId() : null;
        return new LessonDetailResponse(
                lesson.getId(),
                lesson.getTitle(),
                lesson.getSummary(),
                lesson.getVideoUrl(),
                lesson.getModule().getId(),
                lesson.getModule().getTitle(),
                lesson.getPosition(),
                lesson.getGlobalOrder(),
                allLessons.size(),
                completed,
                previousId,
                nextId);
    }

    private void requireOpen(
            NavigationMode mode,
            Lesson lesson,
            Set<Integer> completedOrders,
            List<Lesson> allLessons,
            Set<Long> completedIds) {
        if (!AccessRule.isOpen(mode, lesson.getGlobalOrder(), completedOrders)) {
            throw new LessonLockedException(nextOpenLessonId(allLessons, mode, completedOrders, completedIds));
        }
    }

    private Long nextOpenLessonId(List<Lesson> allLessons, NavigationMode mode, Set<Integer> completedOrders, Set<Long> completedIds) {
        for (Lesson lesson : allLessons) {
            if (!completedIds.contains(lesson.getId()) && AccessRule.isOpen(mode, lesson.getGlobalOrder(), completedOrders)) {
                return lesson.getId();
            }
        }
        return null;
    }

    private Set<Integer> completedOrders(List<Lesson> allLessons, Set<Long> completedIds) {
        Set<Integer> orders = new HashSet<>();
        for (Lesson lesson : allLessons) {
            if (completedIds.contains(lesson.getId())) {
                orders.add(lesson.getGlobalOrder());
            }
        }
        return orders;
    }

    private Lesson find(List<Lesson> allLessons, Long lessonId) {
        return allLessons.stream()
                .filter(lesson -> lesson.getId().equals(lessonId))
                .findFirst()
                .orElseThrow(() -> new BusinessRuleException(HttpStatus.NOT_FOUND, "Lesson not found"));
    }

}
