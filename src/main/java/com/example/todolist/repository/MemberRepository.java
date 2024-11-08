package com.example.todolist.repository;

import com.example.todolist.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByMemberId(String memberId);

    Optional<Member> findByMemberName(String memberName);

    boolean existsByMemberId(String userId);

    boolean existsByMemberName(String userName);

    boolean existsByEmail(String email);
}
