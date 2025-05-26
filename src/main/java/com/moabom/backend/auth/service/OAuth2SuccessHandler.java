package com.moabom.backend.auth.service;

import com.moabom.backend.auth.constants.SecurityConstants;
import com.moabom.backend.auth.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        // JWT 발급
        String accessToken = jwtUtil.generateAccessToken(email);
        String refreshToken = jwtUtil.generateRefreshToken(email);

        // refreshToken DB 저장
        refreshTokenService.save(email, refreshToken, SecurityConstants.REFRESH_TOKEN_EXPIRE);

        Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
        refreshCookie.setHttpOnly(true); // JS에서 접근 불가
        refreshCookie.setSecure(false);   // HTTPS에서만 전송 (개발환경이면 false도 가능)
        refreshCookie.setPath("/");  // 전체 경로에 대해 쿠키 전송
        refreshCookie.setMaxAge((int) SecurityConstants.REFRESH_TOKEN_EXPIRE); // 만료 기간

        response.addCookie(refreshCookie);

        // 프론트엔드로 리다이렉트 (토큰 쿼리스트링 전달)
        String redirectUrl = "http://localhost:5173/oauth2/redirect"
                + "?accessToken=" + accessToken+ "&email=" + email;

        response.sendRedirect(redirectUrl);
    }
}