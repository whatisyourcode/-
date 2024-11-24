package com.example.todolist.dto;

import com.example.todolist.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {

    private Long boardId;

    @NotBlank(message="공백 입력 불가")
    private String content;

    private String createdId;

    private String createdDateTime;

    private String updatedDateTime;

    private String createdUser;

    private boolean completed;

    private Long categoryId;
}
