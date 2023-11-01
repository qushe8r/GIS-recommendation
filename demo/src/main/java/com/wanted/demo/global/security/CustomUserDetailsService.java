package com.wanted.demo.global.security;

import com.wanted.demo.domain.user.domain.User;
import com.wanted.demo.domain.user.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> member = userRepository.findByUsername(username);

        if (member.isEmpty()) {
            throw new UsernameNotFoundException("------------------------- 해당 유저를 찾을 수 없습니다. 유저 아이디:  " + username);
        }

        return new CustomUserDetails(member.get());
    }
}