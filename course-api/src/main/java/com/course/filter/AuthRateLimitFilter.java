package com.course.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class AuthRateLimitFilter extends OncePerRequestFilter {

    static final int ATTEMPT_LIMIT = 8;
    static final int ADDRESS_LIMIT = 32;
    static final Duration WINDOW = Duration.ofMinutes(1);
    static final String CLIENT_HEADER = "X-Course-Client";

    private static final Pattern CLIENT_TOKEN = Pattern.compile("^[A-Za-z0-9_-]{16,64}$");

    private final Clock clock;
    private final ConcurrentHashMap<String, Window> windows = new ConcurrentHashMap<>();

    public AuthRateLimitFilter(Clock clock) {
        this.clock = clock;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        if (!countsTowardLimit(request)) {
            chain.doFilter(request, response);
            return;
        }
        if (!permit(request)) {
            response.setStatus(429);
            response.setContentType("application/json");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.getWriter().write("{\"message\":\"Too many attempts. Wait a minute and try again\"}");
            return;
        }
        chain.doFilter(request, response);
    }

    private boolean countsTowardLimit(HttpServletRequest request) {
        if (!HttpMethod.POST.matches(request.getMethod())) {
            return false;
        }
        String path = request.getRequestURI();
        return "/api/auth/login".equals(path) || "/api/auth/register".equals(path);
    }

    private boolean permit(HttpServletRequest request) {
        long now = clock.millis();
        evictExpired(now);
        String address = clientAddress(request);
        String client = clientToken(request);
        int addressLimit = client == null ? ATTEMPT_LIMIT : ADDRESS_LIMIT;
        if (!allow("address:" + address, addressLimit, now)) {
            return false;
        }
        if (client == null) {
            return true;
        }
        return allow("client:" + client, ATTEMPT_LIMIT, now);
    }

    private void evictExpired(long now) {
        windows.entrySet().removeIf(entry -> now - entry.getValue().startedAt() >= WINDOW.toMillis());
    }

    private boolean allow(String key, int limit, long now) {
        Window window = windows.compute(key, (ignored, existing) -> {
            if (existing == null) {
                return new Window(now, 1);
            }
            return new Window(existing.startedAt(), existing.count() + 1);
        });
        return window.count() <= limit;
    }

    private String clientAddress(HttpServletRequest request) {
        String address = request.getRemoteAddr();
        if (address == null || address.isBlank()) {
            return "unknown";
        }
        return address;
    }

    private String clientToken(HttpServletRequest request) {
        String token = request.getHeader(CLIENT_HEADER);
        if (token == null || !CLIENT_TOKEN.matcher(token).matches()) {
            return null;
        }
        return token;
    }

    private record Window(long startedAt, int count) {
    }
}
