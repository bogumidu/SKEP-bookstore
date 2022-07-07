package com.bsd.skep.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private final UserDetailsService userDetailsService;
    private final String secret;


    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, String secret) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
        this.secret = secret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        if (authentication == null) {
            chain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = null;
        if (request.getCookies() != null) {
            token = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("token")).map(Cookie::getValue).findFirst().orElse(null);
        }
        if (token == null) {
            token = request.getHeader(TOKEN_HEADER);
            if (token == null) {
                return null;
            }
            token = token.replace(TOKEN_PREFIX, "");
        }
        String userName;
        try {
            userName = JWT.require(Algorithm.HMAC512(secret))
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }
        if (userName != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(userDetails);
            return usernamePasswordAuthenticationToken;
        }
        return null;
    }
}
