package com.example.todolist.validator;

import com.example.todolist.dto.MemberDto;
import com.example.todolist.repository.MemberRepository;
import com.example.todolist.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckMemberEmailValidator extends AbstractValidator<MemberDto>{

    private final MemberRepository memberRepository;

    @Override
    protected void doValidate(MemberDto dto, Errors errors) {
        if(memberRepository.existsByEmail(dto.getEmail())){
            errors.rejectValue("email","이메일 중복 오류", "이미 사용중인 이메일입니다.");
        }
    }
}
