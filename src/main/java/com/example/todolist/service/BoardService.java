package com.example.todolist.service;

import com.example.todolist.dto.BoardDto;
import com.example.todolist.entity.Board;
import com.example.todolist.entity.Category;
import com.example.todolist.entity.Member;
import com.example.todolist.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final CategoryService categoryService;

    public List<Board> findBoardsByMember(String memberName) {
        return boardRepository.findBoardsByMember(memberName); // 로그인된 사용자 이름인 게시글만 반환
    }


    public void registerBoard(BoardDto boardDto, Member member) {
        Category category = categoryService.findCategoryById(boardDto.getCategoryId());
        Board board = DTOtoEntityForRegister(boardDto, member, category);
        boardRepository.save(board);
    }

    // 진행 완료된 TO-DO로 변환하는 서비스 로직
    public void completeBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).
                orElseThrow(() -> new RuntimeException("board not found Exception"));
        board.setCompleted(true);   // 진행 완료 TO-DO 로 변경
        board.setEditing(false);    // 수정 상태이면 false 로 변경
        boardRepository.save(board);
    }

    // 진행 중인 TO-DO로 변환하는 서비스 로직
    public void incompleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).
                orElseThrow(() -> new RuntimeException("board not found Exception"));
        board.setCompleted(false);
        boardRepository.save(board);
    }

    // 수정 모드로 변환하는 서비스 로직
    public void setEditMode(Long boardId, boolean editing) {
        Board board = boardRepository.findById(boardId).
                orElseThrow(() -> new RuntimeException("board not found Exception"));
        board.setEditing(editing); // ?
        boardRepository.save(board);
    }

    // 게시판 수정하는 서비스 로직
    public void updatedBoard(BoardDto boardDto) {
        Board board = boardRepository.findById(boardDto.getBoardId()).
                orElseThrow(() -> new RuntimeException("board not found Exception"));
        board.setContent(boardDto.getContent());
        boardRepository.save(board);
    }

    // 게시판 삭제하는 서비스 로직
    public void deleteBoard(Long boardId) {
        boardRepository.deleteById(boardId);
    }


    // DTO -> Entity
    public Board DTOtoEntityForRegister(BoardDto boardDto, Member member, Category category){
        return new Board(
                boardDto.getBoardId(),
                boardDto.getContent(),
                member.getMemberId(),
                LocalDateTime.now().toString().substring(1,10),
                boardDto.getUpdatedDateTime(),
                member.getMemberName(),
                false,
                false,
                category
        );
    }

    // Rediect할 CategoryId 찾기위한 메서드
    public Long getCategoryId(Long boardId){
        return boardRepository.findCategoryIdByBoardId(boardId);
    }

}
