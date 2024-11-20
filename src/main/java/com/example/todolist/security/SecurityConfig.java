package com.example.todolist.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    // 실제 동작 흐름
    // 사용자가 로그인 폼에서 ID와 비밀번호를 입력.
    // Spring Security는 입력받은 ID를 사용해 customMemberDetailsService에서 사용자 정보를 로드.
    // 로드된 사용자 정보와 입력받은 비밀번호를 PasswordEncoder로 비교.
    // 인증 성공 시 사용자에게 권한 정보를 부여하고 인증 세션 생성.
    // 인증 실패 시 로그인 실패 처리 로직 실행.

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        // 사용자가 로그인 요청을 보낼 때, Spring Security는 DaoAuthenticationProvider를 사용해 인증을 진행.
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // 사용자 정보를 가져오는 서비스(UserDetailsService)를 설정
        // customMemberDetailsService는 사용자의 이름, 비밀번호, 권한 등 세부 정보를 가져오는 커스텀 서비스
        provider.setUserDetailsService(customMemberDetailsService);
        // 비밀번호 암호화를 처리하는 PasswordEncoder를 사용하여 암호화된 상태로 저장 및 기존 비밀번호와 비교.
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCrypt 해시 알고리즘 사용
    }

    // AuthenticationSuccessHandler
    // Spring Security의 인터페이스로, 사용자가 로그인에 성공했을 때 추가 작업을 정의할 수 있음.
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
