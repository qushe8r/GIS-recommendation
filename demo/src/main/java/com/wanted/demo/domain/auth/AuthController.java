package com.wanted.demo.domain.auth;

import com.wanted.demo.domain.auth.dto.LoginRequest;
import com.wanted.demo.domain.auth.dto.SignupRequest;
import com.wanted.demo.domain.auth.service.LoginService;
import com.wanted.demo.domain.auth.service.LogoutService;
import com.wanted.demo.domain.auth.service.SignupService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthController {
    private final SignupService signupService;
    private final LoginService loginService;
    private final LogoutService logoutService;

    @PostMapping(value = "/signup")
    public void signup(@RequestBody SignupRequest signupRequest) {
        signupService.signup(signupRequest);
    }

    @PostMapping(value = "/login")
    public void login(@RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse) {
        loginService.login(loginRequest, httpServletResponse);
    }

    @PostMapping(value = "/logout")
    public void logout(HttpServletResponse httpServletResponse) {
        logoutService.logout(httpServletResponse);
    }
}
