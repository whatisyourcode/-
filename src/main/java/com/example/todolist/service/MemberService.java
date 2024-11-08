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

    // Security 사용으로 주석 처리
//    public boolean isValidUser(String id, String password) {
//        Member user = memberRepository.findByMemberId(id);
//        if(user != null){
//            return passwordEncoder.matches(password, user.getPassword());
//        }
//        return false;
//    }

    // Security 사용으로 주석 처리
//    public void registerUser(MemberDto memberDto) {
//        Member user = toUserEntity(memberDto);
//        memberRepository.save(user);
//    }

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



//    public void isMemberIdExist(MemberDto dto) {
//        boolean memberIdDuplicate = memberRepository.existsByMemberId(dto.getMemberId());
//        if(memberIdDuplicate) {
//            throw new IllegalStateException("이미 존재하는 아이디입니다.");
//        }
//    }
//
//    public void isMemberNameExist(MemberDto dto) {
//        boolean memberNameDuplicate = memberRepository.existsByMemberName(dto.getMemberName());
//        if(memberNameDuplicate) {
//            throw new IllegalStateException("이미 존재하는 이름입니다.");
//        }
//    }


}
