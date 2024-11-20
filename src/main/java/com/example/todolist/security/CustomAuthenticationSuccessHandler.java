package com.example.todolist.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        Cookie userCookie = new Cookie("username", username);
        userCookie.setPath("/");
        userCookie.setMaxAge(60 * 60 * 720); // 30일
        userCookie.setSecure(true); // HTTPS에서만 사용
        userCookie.setHttpOnly(true); // JavaScript에서 접근 불가
        response.addCookie(userCookie);

        try {
            response.sendRedirect("/");
        } catch(Exception e){
            e.printStackTrace();
        }

    }
}
