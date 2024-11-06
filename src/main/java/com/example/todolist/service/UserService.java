package com.example.todolist.service;

import com.example.todolist.dto.UserDto;
import com.example.todolist.entity.User;
import com.example.todolist.repository.BoardRepository;
import com.example.todolist.repository.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    public void registerUser(UserDto userDto) {
        User user = toUserEntity(userDto);
        userRepository.save(user);
    }

    public User toUserEntity(UserDto userDto) {
        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setPassword(userDto.getPassword());
        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setCreatedDateTime(LocalDateTime.now().toString()); // 가입 시간 설정
        return user;
    }

    public boolean isUserIdExist(String userId) {
        return userRepository.existsByUserId(userId);
    }

    public boolean isUsernameExist(String username) {
        return userRepository.existsByUserName(username);
    }


}
