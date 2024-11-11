package com.example.todolist.security;

import com.example.todolist.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomMemberDetails implements UserDetails {
    private final Member member;  // Member 객체, 사용자 정보가 담겨 있는 객체

    // 생성자: Member 객체를 받아 CustomMemberDetails 객체를 초기화
    public CustomMemberDetails(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return this.member;
    }

    // 사용자 권한을 반환하는 메서드
    // member의 역할을 "ROLE_" 접두어와 함께 권한 목록으로 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("ROLE_" + member.getRole());
    }

    // 비밀번호 반환
    // member에서 저장된 비밀번호를 반환
    @Override
    public String getPassword() {
        String password = member.getPassword();
        if (password == null) {
            throw new IllegalStateException("Password is not set for user: " + member.getMemberId());
        }
        return password;
    }

    // 사용자 이름 반환
    // member에서 사용자의 이름을 반환 (로그인 시 사용할 아이디)
    @Override
    public String getUsername() {
        return member.getMemberId();
    }


    // 계정 만료 여부 체크
    // 항상 계정이 만료되지 않았다고 반환 (true)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠금 여부 체크
    // 항상 계정이 잠기지 않았다고 반환 (true)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 자격 증명 만료 여부 체크
    // 항상 자격 증명이 만료되지 않았다고 반환 (true)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화 여부 체크
    // 항상 계정이 활성화되어 있다고 반환 (true)
    @Override
    public boolean isEnabled() {
        return true;
    }
}