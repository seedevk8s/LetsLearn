package com.course.learn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll()  // 모든 요청 허용 (개발 환경)
            )
            .csrf(csrf -> csrf.disable())  // CSRF 비활성화 (개발 환경)
            .formLogin(form -> form.disable())  // 기본 로그인 폼 비활성화
            .httpBasic(basic -> basic.disable());  // HTTP Basic 인증 비활성화

        return http.build();
    }
}
