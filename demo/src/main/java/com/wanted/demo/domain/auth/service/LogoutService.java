package com.wanted.demo.domain.auth.service;

import com.wanted.demo.global.util.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LogoutService {
    private final CookieUtil cookieUtil;

    @Transactional
    public void logout(HttpServletResponse response) {
        response.setHeader("Authorization", "");
        cookieUtil.delete("", response);
    }
}
