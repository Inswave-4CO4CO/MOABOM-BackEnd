package com.moabom.backend.controller;

import com.moabom.backend.constants.SecurityConstants;
import com.moabom.backend.model.LoginRequest;
import com.moabom.backend.model.SignupRequest;
import com.moabom.backend.model.User;
import com.moabom.backend.repository.UserRepository;
import com.moabom.backend.service.AuthService;
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

    private final AuthService authService;
    private final UserRepository userRepository;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest request) {
        authService.signup(request);
        return ResponseEntity.ok("회원가입 성공");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {
        String token = authService.login(request);
        Map<String, String> response = new HashMap<>();
        response.put(SecurityConstants.TOKEN_TYPE, SecurityConstants.TOKEN_PREFIX + token);
        return ResponseEntity.ok(response);
    }

    // 사용자 인증
    @GetMapping("/check")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUserId(userDetails.getUsername()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유저 정보 없음");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("userId", user.getUserId());
        response.put("nickName", user.getNickName());
        response.put("userImage", user.getUserImage());
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

