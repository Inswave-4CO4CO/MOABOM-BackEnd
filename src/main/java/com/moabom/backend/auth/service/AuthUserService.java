package com.moabom.backend.auth.service;

import com.moabom.backend.auth.constants.SecurityConstants;
import com.moabom.backend.auth.model.LoginRequest;
import com.moabom.backend.auth.model.SignupRequest;

import com.moabom.backend.auth.model.UserEntity;
import com.moabom.backend.auth.repository.AuthRepository;
import com.moabom.backend.auth.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthUserService {

    private final AuthRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;

    // 회원가입
    public void signup(SignupRequest request) {
        if (userRepository.existsByUserId((request.getUserId()))) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }

        UserEntity user = UserEntity.builder()
                .userId(request.getUserId())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickName(request.getNickName())
                .role("ROLE_USER") // 기본 역할 부여
                .enabled(1)
                .build();

        userRepository.save(user);
    }

    // 로그인 (토큰 발급)
    public Map<String, String> login(LoginRequest request, HttpServletResponse response) {

        Optional<UserEntity> userOptional = userRepository.findByUserId((request.getUserId()));
        UserEntity user = userOptional.orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtUtil.generateAccessToken(user.getUserId());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUserId());
        refreshTokenService.save(user.getUserId(), refreshToken, SecurityConstants.REFRESH_TOKEN_EXPIRE);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // HTTPS 환경에서만.. (일단 false)
        cookie.setPath("/");
        cookie.setMaxAge((int) SecurityConstants.REFRESH_TOKEN_EXPIRE);

        response.addCookie(cookie);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        return tokens;
    }

    // 로그아웃 (Refresh Token 삭제)
    public void logout(String userId, HttpServletResponse response) {
        refreshTokenService.delete(userId);

        // 쿠키 만료
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // 즉시 만료
        response.addCookie(cookie);
    }

    // Access Token 재발급
    public String reissueAccessToken(String refreshToken) {
        if (!jwtUtil.validateRefreshToken(refreshToken)) {
            throw new RuntimeException("Refresh Token이 유효하지 않습니다.");
        }
        String userId = jwtUtil.extractUserId(refreshToken);
        String savedRefreshToken = refreshTokenService.get(userId);
        if (!refreshToken.equals(savedRefreshToken)) {
            throw new RuntimeException("서버에 저장된 Refresh Token과 다릅니다.");
        }
        // 새 Access Token 발급
        return jwtUtil.generateAccessToken(
                userRepository.findByUserId(userId)
                        .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다.")).getUserId()
        );
    }
}

