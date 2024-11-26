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

    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }

    public void setEditMode(Long categoryId, boolean editing) {
        Category category = categoryRepository.findByCategoryId(categoryId);
        category.setEditing(editing);
        categoryRepository.save(category);
    }

    // 카테고리 이름 업데이트
    public void updateCategoryName(Long categoryId, String newName) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(newName);
        category.setEditing(false); // 수정 모드 해제
        categoryRepository.save(category);
    }

    public Category findById(Long categoryId,Member member) {

        if(categoryId == null){
            Category newCategory = new Category();
            newCategory.setName("basic category");
            newCategory.setMember(member);
            categoryRepository.save(newCategory);
            return newCategory;
        }

        return categoryRepository.findByCategoryId(categoryId);
    }
}
