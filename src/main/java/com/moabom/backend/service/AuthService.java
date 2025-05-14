package com.moabom.backend.service;

import com.moabom.backend.model.LoginRequest;
import com.moabom.backend.model.SignupRequest;

import com.moabom.backend.model.User;
import com.moabom.backend.repository.UserRepository;
import com.moabom.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // 회원가입
    public void signup(SignupRequest request) {
        if (userRepository.existsByUserId((request.getUserId()))) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }

        User user = User.builder()
                .userId(request.getUserId())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickName(request.getNickName())
                .role("ROLE_USER") // 기본 역할 부여
                .enabled(1)
                .build();

        userRepository.save(user);
    }

    // 로그인 (토큰 발급)
    public String login(LoginRequest request) {
        Optional<User> userOptional = userRepository.findByUserId((request.getUserId()));
        User user = userOptional.orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return jwtUtil.generateToken(user);
    }
}

