package com.course.controller;

import com.course.config.JwtProperties;
import com.course.config.SecurityConfig;
import com.course.dto.DashboardResponse;
import com.course.dto.LessonDetailResponse;
import com.course.dto.TokenResponse;
import com.course.dto.UserResponse;
import com.course.enumerators.NavigationMode;
import com.course.exception.ApiExceptionHandler;
import com.course.services.AuthService;
import com.course.services.CatalogService;
import com.course.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {AuthController.class, CatalogController.class, HealthController.class})
@Import({SecurityConfig.class, JwtService.class, ApiExceptionHandler.class})
@EnableConfigurationProperties(JwtProperties.class)
@TestPropertySource(properties = {
        "app.jwt.secret=course-local-hmac-secret-with-32-bytes-min",
        "app.jwt.expiration-minutes=60",
        "app.cors.origins=http://localhost:3000, http://127.0.0.1:3000"
})
class HttpApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private CatalogService catalogService;

    @Test
    void healthIsPublic() throws Exception {
        mockMvc.perform(get("/api/health").header("Origin", "http://localhost:3000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"))
                .andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:3000"))
                .andExpect(header().string("X-Frame-Options", "DENY"))
                .andExpect(header().string("Content-Security-Policy",
                        "default-src 'none'; frame-ancestors 'none'; base-uri 'none'"))
                .andExpect(header().string("Referrer-Policy", "no-referrer"));
    }

    @Test
    void optionsArePublic() throws Exception {
        mockMvc.perform(options("/api/dashboard")
                        .header("Origin", "http://127.0.0.1:3000")
                        .header("Access-Control-Request-Method", "GET"))
                .andExpect(status().isOk());
    }

    @Test
    void protectedRouteRequiresAToken() throws Exception {
        mockMvc.perform(get("/api/me"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Sign in to continue"));
    }

    @Test
    void malformedTokenIsRejected() throws Exception {
        mockMvc.perform(get("/api/dashboard").header("Authorization", "Bearer not-a-token"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void registerValidatesAndCreatesTheAccount() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());

        when(authService.register(any())).thenReturn(new TokenResponse("token", "Ana Lima", "ana.lima", NavigationMode.FREE));
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Ana Lima\",\"username\":\"ana.lima\",\"password\":\"senha1234\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("ana.lima"));
    }

    @Test
    void loginRejectsAMalformedBody() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("The request body is invalid"));
    }

    @Test
    void loginReturnsTheToken() throws Exception {
        when(authService.login(any())).thenReturn(new TokenResponse("token", "Ana Lima", "ana.lima", NavigationMode.FREE));
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"ana.lima\",\"password\":\"senha1234\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ana Lima"));
    }

    @Test
    void authenticatedUserCanReadAndChangeTheProfile() throws Exception {
        when(authService.load("ana.lima")).thenReturn(new com.course.entities.UserAccount("Ana Lima", "ana.lima", "hash"));
        when(authService.changeNavigationMode(eq("ana.lima"), eq(NavigationMode.TRAIL)))
                .thenReturn(new UserResponse("Ana Lima", "ana.lima", NavigationMode.TRAIL));

        mockMvc.perform(get("/api/me").header("Authorization", bearer()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("ana.lima"));
        mockMvc.perform(put("/api/me/mode")
                        .header("Authorization", bearer())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"mode\":\"TRAIL\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mode").value("TRAIL"));
    }

    @Test
    void catalogRoutesDelegateToTheService() throws Exception {
        when(catalogService.dashboard("ana.lima")).thenReturn(
                new DashboardResponse("Ana Lima", "ana.lima", NavigationMode.FREE, 2, 1, 4L, java.util.List.of()));
        mockMvc.perform(get("/api/dashboard").header("Authorization", bearer()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ana Lima"));

        when(catalogService.module(eq("ana.lima"), eq(1L))).thenReturn(
                new com.course.dto.ModuleDetailResponse(1L, 1, "Beginner", "Language", "Types", java.util.List.of()));
        mockMvc.perform(get("/api/modules/1").header("Authorization", bearer()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Language"));

        when(catalogService.lesson(eq("ana.lima"), eq(3L))).thenReturn(sampleLesson(false));
        mockMvc.perform(get("/api/lessons/3").header("Authorization", bearer()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("JDK"));

        when(catalogService.complete(eq("ana.lima"), eq(3L))).thenReturn(sampleLesson(true));
        mockMvc.perform(post("/api/lessons/3/completion").header("Authorization", bearer()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    void aBadLessonIdIsAClientError() throws Exception {
        mockMvc.perform(get("/api/lessons/abc").header("Authorization", bearer()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("The request address is invalid"));
    }

    @Test
    void anUnknownRouteIsNotFound() throws Exception {
        mockMvc.perform(get("/api/unknown").header("Authorization", bearer()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("The requested resource was not found"));
    }

    private String bearer() {
        return "Bearer " + jwtService.issue("ana.lima");
    }

    private LessonDetailResponse sampleLesson(boolean completed) {
        return new LessonDetailResponse(3L, "JDK", "summary", null, 1L, "Language", 1, 1, 2, completed, null, 4L);
    }
}
