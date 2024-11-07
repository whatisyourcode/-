package com.example.todolist.validator;

import com.example.todolist.dto.MemberDto;
import com.example.todolist.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckMemberIdValidator extends AbstractValidator<MemberDto>{
    MemberRepository memberRepository;

    @Override
    protected void doValidate(MemberDto dto, Errors errors) {
        if(memberRepository.existsByMemberId(dto.getMemberId())){
            errors.rejectValue("memberId","아이디 중복 오류", "이미 사용중인 닉네임입니다.");
        }
    }
}
