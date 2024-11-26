package com.example.todolist.controller;

import com.example.todolist.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    // 카테고리 삭제
    @PostMapping("/deleteCategory/{categoryId}")
    public String deleteCategory(@PathVariable("categoryId") Long categoryId) {
        categoryService.deleteCategoryById(categoryId);
        return "redirect:/board/todoBoard"; // 삭제 후 todoBoard로 리다이렉트
    }

    // 수정 모드 활성화
    @GetMapping("/editMode/{categoryId}")
    public String editMode(@PathVariable("categoryId") Long categoryId) {
        categoryService.setEditMode(categoryId, true);  // 카테고리 수정 모드 활성화
        return "redirect:/board/todoBoard?categoryId=" + categoryId; // 해당 카테고리 페이지로 리다이렉트
    }

    // 수정된 카테고리 저장
    @PostMapping("/updateCategory/{categoryId}")
    public String updateCategory(@PathVariable("categoryId") Long categoryId, @RequestParam("name") String newName) {
        categoryService.updateCategoryName(categoryId, newName); // 카테고리 이름 수정
        return "redirect:/board/todoBoard?categoryId=" + categoryId; // 수정 후 카테고리 페이지로 리다이렉트
    }

}
