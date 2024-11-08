package com.example.todolist.validator;

import com.example.todolist.dto.MemberDto;
import com.example.todolist.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;


@RequiredArgsConstructor
@Component
public class CheckMemberNameValidator extends AbstractValidator<MemberDto> {


    private final MemberRepository memberRepository;

    @Override
    protected void doValidate(MemberDto dto, Errors errors) {
        if(memberRepository.existsByMemberName(dto.getMemberName())) {
            errors.rejectValue("memberName", "아이디 중복 오류", "이미 사용중인 아이디입니다.");
        }
    }


}
