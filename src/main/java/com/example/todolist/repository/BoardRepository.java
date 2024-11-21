package com.example.todolist.repository;

import com.example.todolist.entity.Board;
import com.example.todolist.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {

    @Query("SELECT b FROM Board b WHERE b.createdUser = :memberName")
    List<Board> findBoardsByMember(@Param("memberName") String memberName);

    List<Board> findBoardsByMemberAndCategoryId(Member member, Long categoryId);
}
