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
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
@Slf4j
public class BoardController {

    private final BoardService boardService;
    private final CategoryService categoryService;

    @GetMapping("/todoBoard")
    public String boardList(@RequestParam(value = "category", required = false)Long categoryId, Model model) {
        // Security로 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();         // 로그인된 사용자의 인증 정보를 가져옵니다.
        CustomMemberDetails customMemberDetails = (CustomMemberDetails) authentication.getPrincipal();   // Spring Security에서 기본적으로 제공하는 Principal을 통해, 현재 인증된 사용자의 정보를 가져옵니다.
        String memberName = customMemberDetails.getMember().getMemberName(); // 로그인된 사용자의 실제 이름을 가져옵니다.


        // 모든 카테고리 출력
        List<Category> categories = categoryService.findAllCategories();
        model.addAttribute("categories", categories);


        if (categoryId == null) {
            categoryId = categoryService.getFirstValidCategoryId(categories);    // 카테고리가 없거나 유효하지 않으면 첫 번째 유효한 카테고리를 선택.
        }


        Member member = customMemberDetails.getMember();
//        List<Board> boardList = boardService.findBoardsByMemberAndCategory(member,categoryId);

        Category category = categoryService.findCategoryById(categoryId);

        model.addAttribute("boardList", category.getBoardList());

        model.addAttribute("boardDto",new BoardDto());  // 초기값 boardDto 가 없으면 에러가 나기 때문에 BoardDto를 임시로 사용.

        return "/board/todoBoard";
    }

    @PostMapping("/addCategory")
    public String addCategory(@RequestParam("categoryName") String categoryName, Model model){

        Category category = new Category();
        category.setName(categoryName);

        categoryService.saveCategory(category);

        List<Category> categories = categoryService.findAllCategories();
        model.addAttribute("categories", categories);

        return "redirect:/board/todoBoard";
    }


    @PostMapping("/addTodoBoard")
    public String addToBoard(@Valid @ModelAttribute("boardDto") BoardDto boardDto, Errors errors, Model model,
                             @AuthenticationPrincipal CustomMemberDetails customMemberDetails) {
        if (errors.hasErrors()) {
            List<Category> categories = categoryService.findAllCategories();
            model.addAttribute("categories", categories);
            return "/board/todoBoard";
        }
        log.info("--------------"+boardDto.getCategoryId());

        Member member = customMemberDetails.getMember();
        boardService.registerBoard(boardDto,member,boardDto.getCategoryId());
        return "redirect:/board/todoBoard";
    }

    @PostMapping("/completedTodoBoard/{boardId}")
    public String completedTodoBoard(@PathVariable("boardId") Long boardId) {
        boardService.completeBoard(boardId);
        return "redirect:/board/todoBoard";
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
