package com.example.todolist.service;

import com.example.todolist.entity.User;
import com.example.todolist.repository.BoardRepository;
import com.example.todolist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public boolean isValidUser(String id, String password) {
        User user = userRepository.findByUserId(id);
        if(user != null){
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }
}
