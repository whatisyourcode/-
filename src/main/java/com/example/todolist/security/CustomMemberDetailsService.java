package com.example.todolist.security;

import com.example.todolist.entity.Member;
import com.example.todolist.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomMemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByMemberId(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));


        // member.getPassword()가 null이면 예외를 던짐
        if (member.getPassword() == null) {
            throw new AuthenticationServiceException("Password is not set for user: " + username);
        }

        return new CustomMemberDetails(member);
    }
}
