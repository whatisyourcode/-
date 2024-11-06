package com.example.todolist.repository;

import com.example.todolist.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    public User findByUserId(String userId);

    boolean existsByUserId(String userId);

    boolean existsByUserName(String userName);
}
