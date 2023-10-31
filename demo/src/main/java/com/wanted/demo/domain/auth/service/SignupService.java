package com.wanted.demo.domain.auth.service;

import com.wanted.demo.domain.auth.dto.SignupRequest;
import com.wanted.demo.domain.user.Role;
import com.wanted.demo.domain.user.User;
import com.wanted.demo.domain.user.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignupService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignupRequest request) {
        Optional<User> user = Optional.ofNullable(User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.MEMBER)
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .lunchService(request.getLunchService())
                .build());

        userRepository.save(user.get());
    }
}
