package com.moabom.backend.user.service;

import com.moabom.backend.auth.model.UserEntity;
import com.moabom.backend.user.model.UserDto;
import com.moabom.backend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private UserEntity getUser(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    // 내 정보 조회
    public UserDto getMyInfo(String userId) {
        UserEntity user = getUser(userId);
        return new UserDto(user.getUserId(), user.getNickName(), user.getUserImage());
    }

    // 내 정보 변경
    @Transactional
    public void updateMyInfo(String userId, String newNickName, String newUserImage) {
        UserEntity user = getUser(userId);

        if (newNickName != null) {
            user.setNickName(newNickName);
        }
        if (newUserImage != null) {
            user.setUserImage(newUserImage);
        }
        userRepository.save(user);
    }

    // 내 비밀번호 변경
    public void changeMyPassword(String userId, String newPassword) {
        UserEntity user = getUser(userId);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
