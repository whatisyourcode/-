package com.example.todolist.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCrypt 해시 알고리즘 사용
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("------- filterChain ------");

        http
                .authorizeHttpRequests()
                .antMatchers("/css/**", "/js/**", "/board/**", "/user/**", "/login/**").permitAll()
                .antMatchers("/member/home","/member/register","/member/registerForm","/member/register/memberIdConfirm/**","/member/register/nameConfirm/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/member/home")  // 로그인 페이지
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/board/todoBoard", true)
                .failureUrl("/member/home?error=true")  // 로그인 실패 시
                .permitAll()
                .and()
                .logout()
                .permitAll();

        http.csrf().disable();
        return http.build();
    }


//    @Bean
//    public DaoAuthenticationProvider authenticationProvider(CustomMemberDetailsService customMemberDetailsService) {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//
//        // 사용자 정보를 조회할 서비스 설정 (CustomUserDetailsService 사용)
//        provider.setUserDetailsService(customMemberDetailsService);
//
////        // 비밀번호를 인코딩할 PasswordEncoder 설정 (passwordEncoder() 메서드 호출)
////        provider.setPasswordEncoder(passwordEncoder());
//
//        return provider;
//    }
}
