//package com.abernathyclinic.medilabo_demographics.config;
//
//import com.abernathyclinic.medilabo_demographics.security.JwtFilter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class WebConfig {
//    @Autowired
//    private JwtFilter jwtFilter;
//
//    @Bean
//    public FilterRegistrationBean<JwtFilter> jwtFilterRegistration() {
//        FilterRegistrationBean<JwtFilter> registration = new FilterRegistrationBean<>();
//        registration.setFilter(jwtFilter);
//        registration.addUrlPatterns("/api/*");
//        return registration;
//    }
//
//}
