package com.example.todolist.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


@Configuration
@EnableWebSecurity
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomMemberDetailsService customMemberDetailsService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customMemberDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCrypt 해시 알고리즘 사용
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("------- filterChain ------");
        http
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests()
                .antMatchers("/css/**", "/js/**").permitAll()
                .antMatchers("/member/home", "/member/register", "/member/registerForm").permitAll()
                .anyRequest().hasRole("MEMBER")  // 나머지 요청은 MEMBER 역할을 가진 사용자만 접근 허용
                .and()
                .formLogin()
                .loginPage("/member/home")  // 로그인 페이지
                .loginProcessingUrl("/login")
                .successHandler(authenticationSuccessHandler())
                .defaultSuccessUrl("/board/todoBoard", true)
                .failureUrl("/member/home?error=true")  // 로그인 실패 시
                .permitAll()
                .and()
                .logout()
                .permitAll();

        http.csrf().disable();
        return http.build();
    }



}
