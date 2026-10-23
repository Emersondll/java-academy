package com.course.filter;

import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class AuthRateLimitFilterTest {

    private static final String BROWSER_A = "browser-a-00000001";
    private static final String BROWSER_B = "browser-b-00000002";

    @Test
    void requestsOutsideLoginAndRegisterAreNotLimited() throws Exception {
        AuthRateLimitFilter filter = new AuthRateLimitFilter(Clock.systemUTC());
        FilterChain chain = mock(FilterChain.class);

        filter.doFilter(request("GET", "/api/auth/login", "10.0.0.1", null), new MockHttpServletResponse(), chain);
        filter.doFilter(request("POST", "/api/health", "10.0.0.1", null), new MockHttpServletResponse(), chain);

        verify(chain, times(2)).doFilter(any(), any());
    }

    @Test
    void eachBrowserHasItsOwnAttemptBudget() throws Exception {
        AuthRateLimitFilter filter = new AuthRateLimitFilter(Clock.systemUTC());
        FilterChain first = mock(FilterChain.class);
        for (int attempt = 0; attempt < AuthRateLimitFilter.ATTEMPT_LIMIT; attempt++) {
            String path = attempt % 2 == 0 ? "/api/auth/login" : "/api/auth/register";
            filter.doFilter(request("POST", path, "172.18.0.1", BROWSER_A), new MockHttpServletResponse(), first);
        }
        MockHttpServletResponse blocked = new MockHttpServletResponse();
        filter.doFilter(request("POST", "/api/auth/login", "172.18.0.1", BROWSER_A), blocked, first);
        assertEquals(429, blocked.getStatus());
        assertTrue(blocked.getContentAsString().contains("Too many attempts"));

        FilterChain second = mock(FilterChain.class);
        filter.doFilter(request("POST", "/api/auth/login", "172.18.0.1", BROWSER_B), new MockHttpServletResponse(), second);
        verify(second).doFilter(any(), any());
    }

    @Test
    void callersWithoutAClientTokenShareTheAddress() throws Exception {
        AuthRateLimitFilter filter = new AuthRateLimitFilter(Clock.systemUTC());
        FilterChain chain = mock(FilterChain.class);
        for (int attempt = 0; attempt < AuthRateLimitFilter.ATTEMPT_LIMIT; attempt++) {
            filter.doFilter(request("POST", "/api/auth/login", "10.0.0.4", attempt == 0 ? "short" : null),
                    new MockHttpServletResponse(), chain);
        }
        MockHttpServletResponse blocked = new MockHttpServletResponse();
        filter.doFilter(request("POST", "/api/auth/login", "10.0.0.4", "%%%"), blocked, chain);
        assertEquals(429, blocked.getStatus());
    }

    @Test
    void theAddressCeilingStopsAFloodOfNewBrowsers() throws Exception {
        AuthRateLimitFilter filter = new AuthRateLimitFilter(Clock.systemUTC());
        for (int attempt = 0; attempt < AuthRateLimitFilter.ADDRESS_LIMIT; attempt++) {
            String client = "browser-flood-" + String.format("%04d", attempt);
            filter.doFilter(request("POST", "/api/auth/login", "10.0.0.9", client), new MockHttpServletResponse(), mock(FilterChain.class));
        }
        MockHttpServletResponse blocked = new MockHttpServletResponse();
        filter.doFilter(request("POST", "/api/auth/login", "10.0.0.9", "browser-flood-extra1"), blocked, mock(FilterChain.class));
        assertEquals(429, blocked.getStatus());
    }

    @Test
    void expiredWindowsAreForgottenAndTheAttemptCanStartAgain() throws Exception {
        MutableClock clock = new MutableClock(Instant.parse("2026-10-13T12:00:00Z"));
        AuthRateLimitFilter filter = new AuthRateLimitFilter(clock);
        filter.doFilter(request("POST", "/api/auth/login", "10.0.0.1", BROWSER_A), new MockHttpServletResponse(), mock(FilterChain.class));
        clock.advance(Duration.ofSeconds(30));
        filter.doFilter(request("POST", "/api/auth/login", "10.0.0.2", BROWSER_B), new MockHttpServletResponse(), mock(FilterChain.class));
        clock.advance(Duration.ofSeconds(40));
        filter.doFilter(request("POST", "/api/auth/login", "10.0.0.3", "browser-c-00000003"), new MockHttpServletResponse(), mock(FilterChain.class));

        ConcurrentHashMap<?, ?> windows = (ConcurrentHashMap<?, ?>) ReflectionTestUtils.getField(filter, "windows");
        assertEquals(4, windows.size());
    }

    @Test
    void blankAndMissingAddressesShareOneBucket() throws Exception {
        AuthRateLimitFilter filter = new AuthRateLimitFilter(Clock.systemUTC());
        FilterChain chain = mock(FilterChain.class);
        filter.doFilter(request("POST", "/api/auth/login", " ", null), new MockHttpServletResponse(), chain);
        filter.doFilter(request("POST", "/api/auth/login", null, null), new MockHttpServletResponse(), chain);
        verify(chain, times(2)).doFilter(any(), any());
        ConcurrentHashMap<?, ?> windows = (ConcurrentHashMap<?, ?>) ReflectionTestUtils.getField(filter, "windows");
        assertTrue(windows.containsKey("address:unknown"));
    }

    private MockHttpServletRequest request(String method, String path, String address, String client) {
        MockHttpServletRequest request = new MockHttpServletRequest(method, path);
        request.setRemoteAddr(address);
        if (client != null) {
            request.addHeader(AuthRateLimitFilter.CLIENT_HEADER, client);
        }
        return request;
    }

    private static final class MutableClock extends Clock {
        private Instant instant;

        private MutableClock(Instant instant) {
            this.instant = instant;
        }

        private void advance(Duration duration) {
            instant = instant.plus(duration);
        }

        @Override
        public ZoneId getZone() {
            return ZoneId.of("UTC");
        }

        @Override
        public Clock withZone(ZoneId zone) {
            return this;
        }

        @Override
        public Instant instant() {
            return instant;
        }
    }
}
