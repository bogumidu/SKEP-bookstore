package com.bsd.spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@Profile("dev")
public class DevWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .sessionManagement()
                .and()
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/api/user")
                .permitAll()
                .antMatchers("/api/user/**", "/api/spyglass", "/api/spyglass/**")
                .authenticated()
//                .anyRequest()
//                .authenticated() // protect all other requests
                .and()
                .rememberMe()
                .alwaysRemember(true)
                .tokenValiditySeconds(60 * 3600)
                .rememberMeCookieName("login")
                .and()
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.setStatus(HttpServletResponse.SC_OK);
                })
                .and()
                .httpBasic()
                .disable()
                .csrf()
                .disable(); // disable cross site request forgery, as we don't use cookies - otherwise ALL PUT, POST, DELETE will get HTTP 403!
    }

}