package com.example.todolist.security;

import com.example.todolist.entity.Member;
import com.example.todolist.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class CustomMemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("memberId : " + username);
        Member member = memberRepository.findByMemberId(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with memeberId: " + username));


        // member.getPassword()가 null이면 예외를 던짐
        if (member.getPassword() == null) {
            throw new AuthenticationServiceException("Password is not set for user: " + username);
        }

        return new CustomMemberDetails(member);
    }
}
