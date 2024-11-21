package com.example.todolist.service;

import com.example.todolist.entity.Category;
import com.example.todolist.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

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

    public Category findCategoryBy(String name) {
        return categoryRepository.findByName(name);
    }

    // 첫 번째 유효한 카테고리 ID를 찾는 메서드
    public Long getFirstValidCategoryId(List<Category> categories) {
        for (Category category : categories) {
            if (category.getId() != null) {
                return category.getId();  // 유효한 카테고리 ID를 반환
            }
        }

        // 유효한 카테고리가 없으면 기본 카테고리 생성
        Category defaultCategory = new Category();
        defaultCategory.setName("Basic Category");
        defaultCategory.setId(1L);
        categoryRepository.save(defaultCategory);

        return defaultCategory.getId();
    }
}
