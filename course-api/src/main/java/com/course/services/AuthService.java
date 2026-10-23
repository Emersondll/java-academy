package com.course.services;

import com.course.dto.LoginRequest;
import com.course.dto.RegisterRequest;
import com.course.dto.TokenResponse;
import com.course.dto.UserResponse;
import com.course.entities.UserAccount;
import com.course.enumerators.NavigationMode;
import com.course.exception.BusinessRuleException;
import com.course.repositories.UserAccountRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserAccountRepository users;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserAccountRepository users, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public TokenResponse register(RegisterRequest request) {
        String username = normalize(request.username());
        if (users.existsByUsername(username)) {
            throw new BusinessRuleException(HttpStatus.CONFLICT, "This username is already registered");
        }
        UserAccount user = new UserAccount(request.name().trim(), username, passwordEncoder.encode(request.password()));
        try {
            users.saveAndFlush(user);
        } catch (DataIntegrityViolationException exception) {
            throw new BusinessRuleException(HttpStatus.CONFLICT, "This username is already registered");
        }
        return tokenFor(user);
    }

    @Transactional(readOnly = true)
    public TokenResponse login(LoginRequest request) {
        UserAccount user = users.findByUsername(normalize(request.username())).orElse(null);
        String hash = user == null ? UNKNOWN_USER_HASH : user.getPasswordHash();
        boolean passwordMatches = passwordEncoder.matches(request.password(), hash);
        if (user == null || !passwordMatches) {
            throw new BusinessRuleException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
        return tokenFor(user);
    }

    @Transactional(readOnly = true)
    public UserAccount load(String username) {
        return users.findByUsername(username)
                .orElseThrow(() -> new BusinessRuleException(HttpStatus.UNAUTHORIZED, "Invalid session"));
    }

    @Transactional
    public UserResponse changeNavigationMode(String username, NavigationMode mode) {
        UserAccount user = load(username);
        user.changeNavigationMode(mode);
        return UserResponse.from(user);
    }

    private TokenResponse tokenFor(UserAccount user) {
        return new TokenResponse(
                jwtService.issue(user.getUsername()),
                user.getName(),
                user.getUsername(),
                user.getNavigationMode());
    }

    private String normalize(String username) {
        return username.trim().toLowerCase();
    }

    private static final String UNKNOWN_USER_HASH =
            "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy";
}
