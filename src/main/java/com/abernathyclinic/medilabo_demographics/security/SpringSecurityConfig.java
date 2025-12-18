package com.abernathyclinic.medilabo_demographics.security;

import io.jsonwebtoken.JwtException;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

import jakarta.servlet.Filter;

import java.util.ArrayList;

@Configuration
public class SpringSecurityConfig {
    private final JwtService jwtService;

    @Autowired
    public SpringSecurityConfig(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Bean
    public SecurityFilterChain filterChain(org.springframework.security.config.annotation.web.builders.HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/patient/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/patient/**").authenticated()
                        .anyRequest().permitAll()
                )
                .addFilterBefore(jwtCookieAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(e -> e.authenticationEntryPoint((req, res, ex) -> {
                    res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }));

        return http.build();
    }

    @Bean
    public Filter jwtCookieAuthFilter() {
        return (req, res, chain) -> {
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) res;

            String token = readJwtFromCookie(request);
            if (token != null) {
                try {
                    var jws = jwtService.parse(token);
                    String username = jws.getBody().getSubject();

                    Object rolesClaim = jws.getBody().get("roles");
                    List<String> roles = new ArrayList<>();
                    if (rolesClaim instanceof List<?>) {
                        for (Object r : (List<?>) rolesClaim) {
                            roles.add(String.valueOf(r));
                        }
                    }

                    var authorities = roles.stream()
                            .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                            .toList();

                    var auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(auth);

                } catch (JwtException e) {
                    SecurityContextHolder.clearContext();
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            }

            chain.doFilter(req, res);
        };
    }

    private String readJwtFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) return null;
        for (Cookie c : request.getCookies()) {
            if ("AUTH_TOKEN".equals(c.getName())) return c.getValue();
        }
        return null;
    }
}