package com.example.todolist.controller;

import com.example.todolist.dto.BoardDto;
import com.example.todolist.entity.Board;
import com.example.todolist.entity.Category;
import com.example.todolist.entity.Member;
import com.example.todolist.repository.MemberRepository;
import com.example.todolist.security.CustomMemberDetails;
import com.example.todolist.service.BoardService;
import com.example.todolist.service.CategoryService;
import com.example.todolist.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
@Slf4j
public class BoardController {

    private final BoardService boardService;
    private final CategoryService categoryService;

    @GetMapping("/todoBoard")
    public String boardList(@RequestParam(value = "categoryId", required = false)Long categoryId, Model model) {
        // Security로 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();         // 로그인된 사용자의 인증 정보를 가져옵니다.
        CustomMemberDetails customMemberDetails = (CustomMemberDetails) authentication.getPrincipal();   // Spring Security에서 기본적으로 제공하는 Principal을 통해, 현재 인증된 사용자의 정보를 가져옵니다.
        Member member = customMemberDetails.getMember(); // 로그인한 사용자 객체

        // 해당 Member의 모든 카테고리 가져오기
        List<Category> categories = categoryService.findByMember(member);
        model.addAttribute("categories", categories);



        // url로부터 해당 categoryId를 가져와서 model에 등록.
        for(Category category : categories) {
            if(category.getCategoryId().equals(categoryId)) {   // 해당 categoryId에 일치하는 카테고리를 가져와서
                List<Board> boardList = category.getBoardList(); // 카테고리의 boardList 데이터를 가져오기.
                model.addAttribute("boardList", boardList);
            }
        }

        // 초기값 boardDto 가 없으면 에러가 나기 때문에 BoardDto를 임시로 사용.
        BoardDto boardDto = new BoardDto();
        boardDto.setCategoryId(categoryId);
        model.addAttribute("boardDto",boardDto);

        return "/board/todoBoard";
    }

    @PostMapping("/addCategory")
    public String addCategory(@RequestParam("categoryName") String categoryName, Model model, @AuthenticationPrincipal CustomMemberDetails customMemberDetails){
        Member member = customMemberDetails.getMember();

        Category category = new Category();
        category.setName(categoryName);
        category.setMember(member);

        categoryService.saveCategory(category);

        return "redirect:/board/todoBoard?categoryId=" + category.getCategoryId();
    }


    @PostMapping("/addTodoBoard")
    public String addToBoard(@Valid @ModelAttribute("boardDto") BoardDto boardDto, Errors errors, Model model,
                             @AuthenticationPrincipal CustomMemberDetails customMemberDetails) {
        if (errors.hasErrors()) {
            List<Category> categories = categoryService.findAllCategories();
            model.addAttribute("categories", categories);
            return "/board/todoBoard";
        }

        Member member = customMemberDetails.getMember();
        boardService.registerBoard(boardDto,member);
        return "redirect:/board/todoBoard?categoryId=" + boardDto.getCategoryId();
    }

    @PostMapping("/completedTodoBoard/{boardId}")
    public String completedTodoBoard(@PathVariable("boardId") Long boardId) {
        boardService.completeBoard(boardId);
        Long categoryId = boardService.getCategoryId(boardId);
        return "redirect:/board/todoBoard?categoryId=" + categoryId;
    }

    @PostMapping("/inCompletedTodoBoard/{boardId}")
    public String inCompletedTodoBoard(@PathVariable("boardId") Long boardId) {
        boardService.incompleteBoard(boardId);

        return "redirect:/board/todoBoard";
    }

    @GetMapping("/editMode/{boardId}")
    public String editMode(@PathVariable("boardId") Long boardId) {
        boardService.setEditMode(boardId,true);
        return "redirect:/board/todoBoard";
    }

    @PostMapping("/updateTodoBoard")
    public String updateTodoBoard(@Valid @ModelAttribute("boardDto") BoardDto boardDto, Errors error,Model model,
                                  @AuthenticationPrincipal CustomMemberDetails customMemberDetails){
        if(error.hasErrors()) {
            List<Board> boardList = boardService.findBoardsByMember(customMemberDetails.getMember().getMemberName());
            model.addAttribute("boardList", boardList);
            return "/board/todoBoard";
        }
        boardService.updatedBoard(boardDto);
        boardService.setEditMode(boardDto.getBoardId(),false);
        return "redirect:/board/todoBoard";
    }

    @PostMapping("deleteTodoBoard/{boardId}")
    public String deleteTodoBoard(@PathVariable("boardId") Long boardId) {
        boardService.deleteBoard(boardId);
        return "redirect:/board/todoBoard";
    }

}
