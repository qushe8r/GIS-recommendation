package com.wanted.demo.domain.auth.service;

import com.wanted.demo.domain.auth.dto.LoginRequest;
import com.wanted.demo.domain.user.User;
import com.wanted.demo.domain.user.UserRepository;
import com.wanted.demo.global.util.CookieUtil;
import com.wanted.demo.global.util.RedisUtil;
import com.wanted.demo.global.util.TokenUtil;
import jakarta.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenUtil tokenUtil;
    private final RedisUtil redisUtil;
    private final CookieUtil cookieUtil;

    @Transactional
    public void login(LoginRequest request, HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new NoSuchElementException("user not found"));

        String accessToken = "Bearer " + tokenUtil.generateAccessToken(String.valueOf(user.getId()));
        response.setHeader("Authorization", accessToken);

        if (redisUtil.getData(String.valueOf(user.getId())) == null) {
            String refreshToken = tokenUtil.generateRefreshToken(String.valueOf(user.getId()));
            cookieUtil.create(refreshToken, response);
        }
    }
}
