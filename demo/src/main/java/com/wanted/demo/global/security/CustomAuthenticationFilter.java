package com.wanted.demo.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.demo.domain.auth.dto.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public CustomAuthenticationFilter(final AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication (final HttpServletRequest request, final HttpServletResponse response) throws AuthenticationException {
        final UsernamePasswordAuthenticationToken authenticationToken;

        try {
            final LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
            log.info("------------------------- 사용자 아이디: " + loginRequest.getUsername() + " 비밀번호: " + loginRequest.getPassword());
            authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        } catch (IOException exception) {
            throw new org.springframework.security.authentication.AuthenticationServiceException("Failed to parse authentication request body", exception);
        }

        setDetails(request, authenticationToken);
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }
}