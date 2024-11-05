package com.example.todolist.controller;

import com.example.todolist.dto.BoardDto;
import com.example.todolist.entity.User;
import com.example.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/home")
    public String login() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpSession session) {
            if(userService.isValidUser(user.getUserId(), user.getPassword())) {
                session.setAttribute("user", user);
                // 로그인 성공시
                return "redirect:/board/todoBoard";
            } else {
                return  "redirect:/login?error";
            }
    }


}
