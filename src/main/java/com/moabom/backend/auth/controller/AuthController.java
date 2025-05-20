package com.moabom.backend.auth.controller;

import com.moabom.backend.auth.model.LoginRequest;
import com.moabom.backend.auth.model.SignupRequest;
import com.moabom.backend.auth.repository.AuthRepository;


import com.moabom.backend.auth.service.AuthUserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUserService authService;
    private final AuthRepository userRepository;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest request) {
        authService.signup(request);
        return ResponseEntity.ok("회원가입 성공");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        try {
            Map<String, String> tokens = authService.login(request, response);
            return ResponseEntity.ok(tokens);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "아이디 또는 비밀번호가 일치하지 않습니다."));
        }
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout(Authentication authentication, HttpServletResponse response) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        authService.logout(userDetails.getUsername(), response);
        return ResponseEntity.ok("로그아웃 성공");
    }

    // access token 재발급
    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> reissue(HttpServletRequest request) {
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    System.out.println(refreshToken);
                    break;
                }
            }
        }
        String newAccessToken = authService.reissueAccessToken(refreshToken);
        Map<String, String> response = new HashMap<>();
        response.put("token", newAccessToken);
        return ResponseEntity.ok(response);
    }

    // 아이디 중복 확인
    @PostMapping("/checkId")
    public ResponseEntity<?> checkUserId(@RequestBody Map<String, String> body) {
        String userId = body.get("userId");
        boolean exists = userRepository.existsByUserId(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("available", !exists);
        return ResponseEntity.ok(response);
    }

}

