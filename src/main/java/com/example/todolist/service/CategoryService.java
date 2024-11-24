package com.example.todolist.service;

import com.example.todolist.entity.Category;
import com.example.todolist.entity.Member;
import com.example.todolist.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // 각 멤버가 갖고 있는 카테고리를 가져오기
    public List<Category> findByMember(Member member) {
        return categoryRepository.findCategoryByMember(member);
    }

    // 카테고리 저장
    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

}
