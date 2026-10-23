package com.course.controller;

import com.course.dto.DashboardResponse;
import com.course.dto.LessonDetailResponse;
import com.course.dto.ModuleDetailResponse;
import com.course.services.CatalogService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/dashboard")
    public DashboardResponse dashboard(@AuthenticationPrincipal String username) {
        return catalogService.dashboard(username);
    }

    @GetMapping("/modules/{id}")
    public ModuleDetailResponse module(@AuthenticationPrincipal String username, @PathVariable Long id) {
        return catalogService.module(username, id);
    }

    @GetMapping("/lessons/{id}")
    public LessonDetailResponse lesson(@AuthenticationPrincipal String username, @PathVariable Long id) {
        return catalogService.lesson(username, id);
    }

    @PostMapping("/lessons/{id}/completion")
    public LessonDetailResponse complete(@AuthenticationPrincipal String username, @PathVariable Long id) {
        return catalogService.complete(username, id);
    }
}
