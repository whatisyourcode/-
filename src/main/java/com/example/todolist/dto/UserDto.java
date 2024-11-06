package com.example.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "이메일 주소를 입력해주세요.")
    private String userId;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String userName;

    @NotBlank(message = "패스워드를 입력해주세요.")
    private String password;

    private String confirmPassword;

    @Email(message = "올바른 이메일 주소를 입력해주세요.")
    @NotBlank(message = "패스워드를 입력해주세요.")
    private String email;

    private String createdDateTime;
}
