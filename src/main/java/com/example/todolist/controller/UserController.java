package com.example.todolist.controller;

import com.example.todolist.dto.BoardDto;
import com.example.todolist.dto.UserDto;
import com.example.todolist.entity.User;
import com.example.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/registerForm")
    public String registerForm() {
        return "/user/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserDto userDto,Model model) {
        if(!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            model.addAttribute("error", "Passwords do not match");
            return "/user/registerForm";
        }

        userService.registerUser(userDto);
        return "redirect:/user/home";
    }

    @GetMapping("/register/userIdConfirm/{userId}")
    public ResponseEntity<Boolean> checkUserId(@PathVariable String userId){
        return ResponseEntity.ok(userService.isUserIdExist(userId));
    }

    @GetMapping("/register/nameConfirm/{name}")
    public ResponseEntity<Boolean> checkName(@PathVariable String name){
        return ResponseEntity.ok(userService.isUsernameExist(name));
    }

}
