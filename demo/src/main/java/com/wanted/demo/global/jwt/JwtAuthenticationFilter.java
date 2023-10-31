package com.wanted.demo.global.jwt;

import com.wanted.demo.domain.user.User;
import com.wanted.demo.domain.user.UserRepository;
import com.wanted.demo.global.util.TokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenUtil tokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        if (requestURI.equals("/auth/signup") ||
                requestURI.equals("/auth/login") ||
                requestURI.equals("/auth/logout") ||
                requestURI.equals("/token/refresh")
        ) {

            filterChain.doFilter(request, response);
            return;
        }

        // 헤더에서 JWT 토큰 추출
        String authorizationHeader = request.getHeader("Authorization");
        String accessToken = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            accessToken = authorizationHeader.substring(7); // "Bearer " 부분 제거
        }

        // access 토큰 유효성 검사
        tokenUtil.isValidToken(accessToken);
        // 토큰 인증
        tokenUtil.getAuthenticationFromToken(accessToken);
        filterChain.doFilter(request, response);
    }
}