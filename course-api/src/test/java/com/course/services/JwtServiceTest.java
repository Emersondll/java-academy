package com.course.services;

import com.course.config.JwtProperties;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JwtServiceTest {

    private final JwtService jwtService = new JwtService(new JwtProperties("course-local-hmac-secret-with-32-bytes-min", 15));

    @Test
    void issuedTokenCarriesTheUsername() {
        String token = jwtService.issue("ana.lima");
        assertEquals("ana.lima", jwtService.readUsername(token));
    }

    @Test
    void malformedTokenIsRejected() {
        assertThrows(JwtException.class, () -> jwtService.readUsername("not-a-token"));
    }
}
