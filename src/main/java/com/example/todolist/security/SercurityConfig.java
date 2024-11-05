package com.example.todolist.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;


@Configuration
@EnableWebSecurity
@Slf4j
public class SercurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCrypt 해시 알고리즘 사용
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("------- filterChain ------");
        http.authorizeHttpRequests()
                .antMatchers("/css/**").permitAll() // CSS 파일에 대한 요청 허용
                .antMatchers("/js/**").permitAll() // JS 파일에 대한 요청 허용
                .antMatchers("/user/home").permitAll() // 로그인 페이지에 대한 요청 허용
                .antMatchers("/board/**").permitAll()
                .anyRequest().authenticated() // 나머지 요청은 인증된 사용자만 허용
                .and()
                .formLogin() // 폼 로그인 활성화
                .loginPage("/user/home") // 사용자 정의 로그인 페이지 설정
                .permitAll() // 로그인 페이지 접근 허용
                .and()
                .logout() // 로그아웃 설정
                .permitAll(); // 로그아웃 페이지 접근 허용

        http.csrf().disable(); // CSRF 보호 비활성화 (필요에 따라 조정)
        return http.build();
    }

}
