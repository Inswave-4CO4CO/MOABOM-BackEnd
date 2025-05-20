package com.moabom.backend.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;


@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final StringRedisTemplate redisTemplate;

    // Refresh Token 저장 (userId를 key로, refreshToken을 value로)
    public void save(String userId, String refreshToken, long expireSeconds) {
        redisTemplate.opsForValue().set(userId, refreshToken, Duration.ofSeconds(expireSeconds));
    }

    // Refresh Token 조회
    public String get(String userId) {
        return redisTemplate.opsForValue().get(userId);
    }

    // Refresh Token 삭제 (로그아웃 등)
    public void delete(String userId) {
        redisTemplate.delete(userId);
    }
}
