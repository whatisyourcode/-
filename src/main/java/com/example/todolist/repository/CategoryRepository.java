package com.example.todolist.repository;

import com.example.todolist.entity.Category;
import com.example.todolist.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);

    Category findByCategoryId(long categoryId);

    List<Category> findCategoryByMember(Member member);

}
