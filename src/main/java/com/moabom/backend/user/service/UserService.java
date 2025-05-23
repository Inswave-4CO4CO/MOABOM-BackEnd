package com.moabom.backend.user.service;

import com.moabom.backend.auth.model.UserEntity;
import com.moabom.backend.user.model.UserDto;
import com.moabom.backend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${upload.local.dir}")
    private String uploadDir;

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

    public String saveImageAndGetUrl(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        String newFileName = System.currentTimeMillis() + "_" + originalFileName;
        Path filePath = Paths.get(uploadDir, newFileName); // 주입받은 경로 사용

        try {
            Files.createDirectories(filePath.getParent());
            file.transferTo(filePath.toFile());
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패", e);
        }

        // 저장된 파일의 URL 반환 (예: /uploads/xxx.jpg)
        return "/uploads/" + newFileName;
    }


    // 내 정보 변경
    @Transactional
    public void updateMyInfo(String userId, String newNickName, MultipartFile newUserImage) {
        UserEntity user = getUser(userId);

        if (newNickName != null) {
            user.setNickName(newNickName);
        }
        if (newUserImage != null && !newUserImage.isEmpty()) {
            // 1. 이미지를 서버에 저장하고
            String imageUrl = saveImageAndGetUrl(newUserImage);
            // 2. DB에 이미지 URL 저장
            user.setUserImage(imageUrl);
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
