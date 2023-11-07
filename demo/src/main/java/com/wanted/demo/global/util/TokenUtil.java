package com.wanted.demo.global.util;

import com.wanted.demo.domain.user.UserRepository;
import com.wanted.demo.domain.user.domain.User;
import com.wanted.demo.global.security.CustomUserDetails;
import com.wanted.demo.global.security.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenUtil {

    private final RedisUtil redisUtil;
    private final UserRepository userRepository;

    @Value("${secret.key}")
    private String SECRET_KEY;

    // Access 토큰 유효시간 15 분
    static final long AccessTokenValidTime = 15 * 60 * 1000L;

    public String generateAccessToken(String userId) {
        Claims claims = Jwts.claims().setSubject(userId);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + AccessTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // 리프레시토큰 발행
    public String generateRefreshToken(String userId) {
        Claims claims = Jwts.claims().setSubject(userId);
        Date now = new Date();

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        // 저장
        redisUtil.setDataExpire(userId, refreshToken, Duration.ofDays(7)); // rt 는 7 일로 두었습니다.
        return refreshToken;
    }

    // 토큰의 유효성 검사
    public boolean isValidToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);

            return claims.getBody()
                    .getExpiration()
                    .after(new Date());

        } catch (ExpiredJwtException e) { // 어세스 토큰 만료
            throw  e;
        } catch (Exception e) {
            throw e;
        }
    }

    // 어세스 토큰에서 유저 아이디 얻기
    public String getUserIdFromToken (String token) {
        String userId = Jwts.parser().setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody().getSubject();
        log.info("--------------TokenUtil.getUserIdFromAccessToken: " + userId);
        return userId;
    }


    // 어세스 토큰 재발행
    public String refreshAccessToken(String refreshToken) {
        String userId = getUserIdFromToken(refreshToken); // 리프레시 토큰의 사용자 정보를 기반으로 새로운 어세스 토큰 발급
        return generateAccessToken(userId);
    }

    // 어세스 토큰을 헤더에서 추출하는 메서드
    public String getJWTTokenFromHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        log.info("-------------------------Authorization Header: " + authorizationHeader);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);  // "Bearer " 문자열 이후의 토큰을 반환
        }
        return null;
    }

    // 토큰 인증과정 밟기
    public void getAuthenticationFromToken(String accessToken) {
        Long userId = Long.valueOf(getUserIdFromToken(accessToken));
        User user = userRepository.findById(userId).get();
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        // JWT 토큰이 유효하면, 사용자 정보를 연결 세션에 추가
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(customUserDetails, accessToken, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}