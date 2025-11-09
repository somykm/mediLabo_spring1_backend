//package com.abernathyclinic.medilabo_demographics.security;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//public class JwtFilter extends OncePerRequestFilter{
//
//        private final String SECRET = "mysecretkey";
//
//        @Override
//        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
//                                        FilterChain filterChain) throws ServletException, IOException {
//            String authHeader = request.getHeader("Authorization");
//            if (authHeader != null && authHeader.startsWith("Bearer ")) {
//                String jwt = authHeader.substring(7);
//                try {
//                    Claims claims = Jwts.parserBuilder()
//                            .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))
//                            .build()
//                            .parseClaimsJws(jwt)
//                            .getBody();
//                    request.setAttribute("claims", claims);
//                } catch (Exception e) {
//                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                    return;
//                }
//            }
//            filterChain.doFilter(request, response);
//        }
//    }
