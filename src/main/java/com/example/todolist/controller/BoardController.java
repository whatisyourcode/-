package com.example.todolist.controller;

import com.example.todolist.dto.BoardDto;
import com.example.todolist.entity.Board;
import com.example.todolist.entity.Member;
import com.example.todolist.repository.MemberRepository;
import com.example.todolist.security.CustomMemberDetails;
import com.example.todolist.service.BoardService;
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
    private final MemberService memberService;
    private final MemberRepository memberRepository;


    @GetMapping("/todoBoard")
    public String boardList(Model model) {
        // 로그인된 사용자의 인증 정보를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Spring Security에서 기본적으로 제공하는 Principal을 통해, 현재 인증된 사용자의 정보를 가져옵니다.
        // authentication.getPrincipal()은 기본적으로 UserDetails 객체를 반환합니다.
        CustomMemberDetails customMemberDetails = (CustomMemberDetails) authentication.getPrincipal();

        // CustomMemberDetails는 UserDetails를 구현한 클래스이므로, 이를 통해 사용자에 대한 추가 정보를 쉽게 가져올 수 있습니다.
        String memberName = customMemberDetails.getMember().getMemberName(); // 로그인된 사용자의 실제 이름을 가져옵니다.

        // 로그인된 사용자만의 게시글을 가져옴
        List<Board> boardList = boardService.findBoardsByMember(memberName);

        model.addAttribute("boardList",boardList);

        // 초기값 boardDto 가 없으면 에러가 나기 때문에 BoardDto를 임시로 사용.
        model.addAttribute("boardDto",new BoardDto());

        return "/board/todoBoard";
    }

    @PostMapping("/addTodoBoard")
    public String addToBoard(@Valid @ModelAttribute("boardDto") BoardDto boardDto, Errors errors, Model model,
                             @AuthenticationPrincipal CustomMemberDetails customMemberDetails) {
        if (errors.hasErrors()) {
            List<Board> boardList = boardService.findBoardsByMember(customMemberDetails.getMember().getMemberName());
            model.addAttribute("boardList", boardList);
            return "/board/todoBoard";
        }
        Member member = customMemberDetails.getMember();
        Board board = boardService.DTOtoEntityForRegister(boardDto,member);
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
