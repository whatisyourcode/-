package com.example.todolist.service;

import com.example.todolist.controller.BoardController;
import com.example.todolist.dto.BoardDto;
import com.example.todolist.entity.Board;
import com.example.todolist.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;


    public List<Board> findAllBoardList() {
        List<Board> boardList = boardRepository.findAll();
        return boardList;
    }

    public void registerBoard(Board board) {
        boardRepository.save(board);
    }

    public void completeBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).
                orElseThrow(() -> new RuntimeException("board not found Exception"));
        board.setCompleted(true);
        boardRepository.save(board);
    }

    public void incompleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).
                orElseThrow(() -> new RuntimeException("board not found Exception"));
        board.setCompleted(false);
        boardRepository.save(board);
    }

    public void setEditMode(Long boardId, boolean editing) {
        Board board = boardRepository.findById(boardId).
                orElseThrow(() -> new RuntimeException("board not found Exception"));
        board.setEditing(editing);
        boardRepository.save(board);
    }

    public void updatedBoard(BoardDto boardDto) {
        Board board = boardRepository.findById(boardDto.getBoardId()).
                orElseThrow(() -> new RuntimeException("board not found Exception"));
        board.setContent(boardDto.getContent());
        boardRepository.save(board);
    }

    public void deleteBoard(Long boardId) {
        boardRepository.deleteById(boardId);
    }


    // DTO -> Entity
    public Board DTOtoEntityForRegister(BoardDto boardDto){
        return new Board(
                boardDto.getBoardId(),
                boardDto.getContent(),
                boardDto.getCreatedId(),
                LocalDateTime.now().toString().substring(1,10),
                boardDto.getUpdatedDateTime(),
                "admin",
                false,
                false
        );
    }

}
