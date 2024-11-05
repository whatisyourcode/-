package com.example.todolist.controller;

import com.example.todolist.dto.BoardDto;
import com.example.todolist.entity.Board;
import com.example.todolist.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;


    @GetMapping("/todoBoard")
    public String boardList(Model model) {
        List<Board> boardList = boardService.findAllBoardList();
        model.addAttribute("boardList",boardList);
        model.addAttribute("boardDto",new BoardDto());
        return "/board/todoBoard";
    }

    @PostMapping("/addTodoBoard")
    public String addToBoard(@Valid @ModelAttribute("boardDto") BoardDto boardDto, Errors errors, Model model) {
        if (errors.hasErrors()) {
            List<Board> boardList = boardService.findAllBoardList();
            model.addAttribute("boardList",boardList);
            return "/board/todoBoard";
        }
        Board board = boardService.DTOtoEntityForRegister(boardDto);
        boardService.registerBoard(board);
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
    public String updateTodoBoard(@ModelAttribute("boardDto") BoardDto boardDto){
        if (boardDto.getBoardId() == null) {
            System.out.println("boardId is null");
            // 추가적인 디버깅 정보를 로깅
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
