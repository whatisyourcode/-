package com.example.todolist.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="member")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String memberId;

    private String password;

    private String memberName;

    private String email;

    private String createdDateTime;

    private String role;

    @OneToMany(mappedBy = "member")
    @ToString.Exclude
    private List<Category> categories = new ArrayList<>();
}
