package com.bsd.skep.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bsd.skep.model.LoginCredentials;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

public class JsonObjectAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;
    private String secret;

    public JsonObjectAuthenticationFilter(ObjectMapper objectMapper, String secret) {
        setFilterProcessesUrl("/api/auth/login");
        this.objectMapper = objectMapper;
        this.secret = secret;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!"POST".equals(request.getMethod())) {
            throw new AuthenticationException("Authentication method not allowed: " + request.getMethod()) {
                @Override
                public String getMessage() {
                    return "Authentication method not allowed: " + request.getMethod();
                }
            };
        }
        try {
            BufferedReader reader = request.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            LoginCredentials authRequest = objectMapper.readValue(sb.toString(), LoginCredentials.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
            setDetails(request, token);
            Authentication authenticate = this.getAuthenticationManager().authenticate(token);
            String signed = JWT.create()
                    .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24)))
                    .withIssuedAt(new Date())
                    .withSubject(authRequest.getUsername())
                    .sign(Algorithm.HMAC512(secret));
            Cookie cookie = new Cookie("token", signed);
            cookie.setPath("/");
            response.addCookie(cookie);
            return authenticate;
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
