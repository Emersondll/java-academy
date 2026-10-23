package com.course.services;

import com.course.config.JwtProperties;
import com.course.dto.LoginRequest;
import com.course.dto.RegisterRequest;
import com.course.dto.TokenResponse;
import com.course.entities.UserAccount;
import com.course.enumerators.NavigationMode;
import com.course.exception.BusinessRuleException;
import com.course.repositories.UserAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserAccountRepository users;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService(users, passwordEncoder, new JwtService(new JwtProperties("course-local-hmac-secret-with-32-bytes-min", 60)));
    }

    @Test
    void registerStoresANormalizedUserAndReturnsAToken() {
        when(users.existsByUsername("ana.lima")).thenReturn(false);
        TokenResponse token = authService.register(new RegisterRequest(" Ana Lima ", "Ana.Lima", "senha1234"));

        ArgumentCaptor<UserAccount> saved = ArgumentCaptor.forClass(UserAccount.class);
        verify(users).saveAndFlush(saved.capture());
        assertEquals("Ana Lima", saved.getValue().getName());
        assertEquals("ana.lima", saved.getValue().getUsername());
        assertTrue(passwordEncoder.matches("senha1234", saved.getValue().getPasswordHash()));
        assertEquals("ana.lima", token.username());
        assertEquals("Ana Lima", token.name());
        assertEquals(NavigationMode.FREE, token.mode());
        assertEquals("ana.lima", new JwtService(new JwtProperties("course-local-hmac-secret-with-32-bytes-min", 60)).readUsername(token.token()));
    }

    @Test
    void registerRejectsADuplicateUsername() {
        when(users.existsByUsername("ana.lima")).thenReturn(true);
        BusinessRuleException error = assertThrows(BusinessRuleException.class,
                () -> authService.register(new RegisterRequest("Ana", "ana.lima", "senha1234")));
        assertEquals(409, error.getStatus().value());
    }

    @Test
    void registerTurnsAConcurrentDuplicateIntoAConflict() {
        when(users.existsByUsername("ana.lima")).thenReturn(false);
        when(users.saveAndFlush(any())).thenThrow(new DataIntegrityViolationException("duplicate"));

        BusinessRuleException error = assertThrows(BusinessRuleException.class,
                () -> authService.register(new RegisterRequest("Ana", "ana.lima", "senha1234")));

        assertEquals(409, error.getStatus().value());
    }

    @Test
    void loginAcceptsTheRightPassword() {
        UserAccount user = new UserAccount("Ana Lima", "ana.lima", passwordEncoder.encode("senha1234"));
        when(users.findByUsername("ana.lima")).thenReturn(Optional.of(user));

        TokenResponse token = authService.login(new LoginRequest(" ANA.LIMA ", "senha1234"));

        assertEquals("ana.lima", token.username());
        assertEquals(NavigationMode.FREE, token.mode());
    }

    @Test
    void loginRejectsAWrongPassword() {
        UserAccount user = new UserAccount("Ana Lima", "ana.lima", passwordEncoder.encode("senha1234"));
        when(users.findByUsername("ana.lima")).thenReturn(Optional.of(user));
        assertThrows(BusinessRuleException.class, () -> authService.login(new LoginRequest("ana.lima", "outra-senha")));
    }

    @Test
    void loginRejectsAnUnknownUser() {
        when(users.findByUsername("ana.lima")).thenReturn(Optional.empty());
        assertThrows(BusinessRuleException.class, () -> authService.login(new LoginRequest("ana.lima", "senha1234")));
    }

    @Test
    void loadRejectsAnUnknownSession() {
        when(users.findByUsername("ana.lima")).thenReturn(Optional.empty());
        assertThrows(BusinessRuleException.class, () -> authService.load("ana.lima"));
    }

    @Test
    void changeNavigationModeUpdatesTheAccount() {
        UserAccount user = new UserAccount("Ana Lima", "ana.lima", "hash");
        when(users.findByUsername("ana.lima")).thenReturn(Optional.of(user));

        var response = authService.changeNavigationMode("ana.lima", NavigationMode.TRAIL);

        assertEquals(NavigationMode.TRAIL, user.getNavigationMode());
        assertEquals("Ana Lima", response.name());
        assertEquals(NavigationMode.TRAIL, response.mode());
    }
}
