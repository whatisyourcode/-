package com.example.todolist.controller;

import com.example.todolist.dto.MemberDto;
import com.example.todolist.service.MemberService;
import com.example.todolist.validator.CheckMemberEmailValidator;
import com.example.todolist.validator.CheckMemberIdValidator;
import com.example.todolist.validator.CheckMemberNameValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final CheckMemberIdValidator checkMemberIdValidator;
    private final CheckMemberNameValidator checkMemberNameValidator;
    private final CheckMemberEmailValidator checkMemberEmailValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // 여러 검증기를 추가하여 모두 적용
        binder.addValidators(checkMemberIdValidator, checkMemberNameValidator, checkMemberEmailValidator);
    }

    @GetMapping("/home")
    public String login() {
        return "/member/login";
    }

//    Spring Security 로 인한 주석처리.
//    @PostMapping("/login")
//    public String login(@ModelAttribute User user, HttpSession session) {
//        System.out.println(userService.isValidUser(user.getUserId(), user.getPassword()));
//        System.out.println("------------------------------------");
//            if(userService.isValidUser(user.getUserId(), user.getPassword())) {
//                session.setAttribute("user", user);
//                // 로그인 성공시
//                return "redirect:/board/todoBoard";
//            } else {
//                return  "redirect:/login?error";
//            }
//    }



    @GetMapping("/registerForm")
    public String registerForm(Model model) {
        model.addAttribute("memberDto", new MemberDto()); // 초기 빈 객체 추가.
        return "/member/register";
    }

    @PostMapping("/register")
    public String register(@Valid MemberDto memberDto, Errors errors, Model model) {
        if(errors.hasErrors()) {
            // 회원가입 실패시 입력 데이터 값을 유지
            model.addAttribute("memberDto", memberDto);

            // 유효성 통과 못한 필드와 메시지를 핸들링
            Map<String,String> validatorResult = memberService.validateHandling(errors);

            // 각 오류 메시지를 필드 이름과 함께 모델에 추가하여 화면에서 표시할 수 있도록 함
            for(String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            return "/member/register";
        }

        if(!memberDto.getPassword().equals(memberDto.getConfirmPassword())) {
            model.addAttribute("passwordError", "Passwords do not match");
            return "/member/register";
        }

        memberService.registerMember(memberDto);
        return "redirect:/member/home";
    }

//    @GetMapping("/register/memberIdConfirm/{memberId}")
//    public ResponseEntity<Boolean> checkMemberId(@PathVariable String memberId){
//        return ResponseEntity.ok(memberService.isUserIdExist(memberId));
//    }
//
//    @GetMapping("/register/nameConfirm/{name}")
//    public ResponseEntity<Boolean> checkName(@PathVariable String name){
//        return ResponseEntity.ok(memberService.isUsernameExist(name));
//    }

}
