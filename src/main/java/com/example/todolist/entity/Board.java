package com.example.todolist.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "todo_board")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @NotBlank(message = "내용은 필수 입력입니다.")
    private String content;

    private String createdId;

    private String createdDateTime;

    private String updatedDateTime;

    private String createdUser;

    private boolean completed;

    private boolean editing = false;

    @ManyToOne
    @JoinColumn(name ="memberId")
    private Member member;
}
