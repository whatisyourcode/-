package com.example.todolist.service;

import com.example.todolist.dto.MemberDto;
import com.example.todolist.entity.Member;
import com.example.todolist.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public Member registerMember(MemberDto memberDto) {
        // 비밀번호 암호화
        Member member = toMemberEntity(memberDto);
        return memberRepository.save(member);
    }

    public Member toMemberEntity(MemberDto memberDto) {
        Member member = new Member();
        member.setMemberId(memberDto.getMemberId());
        member.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        member.setMemberName(memberDto.getMemberName());
        member.setEmail(memberDto.getEmail());
        member.setCreatedDateTime(LocalDateTime.now().toString()); // 가입 시간 설정
        member.setRole("MEMBER");
        return member;
    }

    public Map<String,String> validateHandling(Errors errors) {
        Map<String,String> validatorResult = new HashMap<>();

        for(FieldError fieldError : errors.getFieldErrors()) {
            System.out.println(fieldError.getField());
            String validKeyName = String.format("valid_%s", fieldError.getField());
            validatorResult.put(validKeyName, fieldError.getDefaultMessage());
        }
        return validatorResult;
    }

}
