package com.wanted.demo.global.jwt;

import com.wanted.demo.global.util.CookieUtil;
import com.wanted.demo.global.util.RedisUtil;
import com.wanted.demo.global.util.TokenUtil;
import jakarta.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Cookie;


@Slf4j
@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class JwtController {

    private final TokenUtil tokenUtil;
    private final CookieUtil cookieUtil;
    private final RedisUtil redisUtil;

    @GetMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Cookie refreshTokenCookie = cookieUtil.getCookie(request, "refreshToken");
        if (refreshTokenCookie == null) { // 쿠키에 rt 가 존재하지 않는 경우
            throw new NoSuchElementException("refresh token does not exist");
        }

        String refreshToken = refreshTokenCookie.getValue();
        log.info("refreshToken: " + refreshToken);
        String userId = tokenUtil.getUserIdFromToken(refreshToken);
        log.info("userId: " + userId);

        if (redisUtil.getData(userId) == null) { // 리프레시 토큰이 만료되어 레디스에서 사라진 경우
            throw new NoSuchElementException("refresh token expired");
        }

        // 어세스 토큰 재발급
        String newAccessToken;
        newAccessToken = tokenUtil.refreshAccessToken(refreshToken);
        tokenUtil.getAuthenticationFromToken(newAccessToken);

        // 헤더에 추가
        HttpHeaders responseHeaders = new HttpHeaders();
        newAccessToken = "Bearer " + newAccessToken;
        responseHeaders.set("Authorization", newAccessToken);

        return ResponseEntity.ok().headers(responseHeaders).build();
    }
}