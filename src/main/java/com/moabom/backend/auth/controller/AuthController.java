package com.moabom.backend.auth.controller;

import com.moabom.backend.auth.model.LoginRequest;
import com.moabom.backend.auth.model.SignupRequest;
import com.moabom.backend.auth.repository.AuthRepository;

import com.moabom.backend.auth.service.AuthUserService;
import io.jsonwebtoken.ExpiredJwtException;
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
        Map<String, String> tokens = authService.login(request, response);
        return ResponseEntity.ok(tokens);
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
                    break;
                }
            }
        }
        try {
            String newAccessToken = authService.reissueAccessToken(refreshToken);
            Map<String, String> response = new HashMap<>();
            response.put("token", newAccessToken);
            return ResponseEntity.ok(response);
        } catch (ExpiredJwtException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "refresh token expired");
            return ResponseEntity.status(401).body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "invalid refresh token");
            return ResponseEntity.status(401).body(error);
        }
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

