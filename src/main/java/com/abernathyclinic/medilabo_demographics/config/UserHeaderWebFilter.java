//package com.abernathyclinic.medilabo_demographics.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.ReactiveSecurityContextHolder;
//import org.springframework.security.core.context.SecurityContextImpl;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Mono;
//
//@Configuration
//public class UserHeaderWebFilter {
//    @Bean
//    public WebFilter userHeaderFilter() {
//        return (ServerWebExchange exchange, WebFilterChain chain) -> {
//            String username = exchange.getRequest().getHeaders().getFirst("X-User-Name");
//
//            if (username != null) {
//                Authentication auth = new UsernamePasswordAuthenticationToken(username, null, null);
//                return chain.filter(exchange)
//                        .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(
//                                Mono.just(new SecurityContextImpl(auth))
//                        ));
//            }
//
//            return chain.filter(exchange);
//        };
//    }
//}
