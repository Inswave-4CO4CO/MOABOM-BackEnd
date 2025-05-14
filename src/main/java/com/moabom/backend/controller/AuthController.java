package com.moabom.backend.controller;

import com.moabom.backend.constants.SecurityConstants;
import com.moabom.backend.model.LoginRequest;
import com.moabom.backend.model.SignupRequest;
import com.moabom.backend.repository.UserRepository;
import com.moabom.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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
    public ResponseEntity<Map<String, String>> check(@RequestParam String token) {

        return null;
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

