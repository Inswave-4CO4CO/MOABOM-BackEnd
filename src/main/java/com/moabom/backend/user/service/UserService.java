package com.moabom.backend.user.service;

import com.moabom.backend.auth.model.UserEntity;
import com.moabom.backend.user.model.UserDto;
import com.moabom.backend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3Service s3Service;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, S3Service s3Service) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.s3Service = s3Service;
    }

    private UserEntity getUser(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    public UserDto getMyInfo(String userId) {
        UserEntity user = getUser(userId);
        return new UserDto(user.getUserId(), user.getNickName(), user.getUserImage());
    }

    // 이미지 → S3 업로드
    public String saveImageAndGetUrl(MultipartFile file) {
        return s3Service.uploadFile(file);
    }

    @Transactional
    public void updateMyInfo(String userId, String newNickName, MultipartFile newUserImage) {
        UserEntity user = getUser(userId);

        if (newNickName != null) {
            user.setNickName(newNickName);
        }
        if (newUserImage != null && !newUserImage.isEmpty()) {
            String imageUrl = saveImageAndGetUrl(newUserImage);
            user.setUserImage(imageUrl);
        }
        userRepository.save(user);
    }

    public void changeMyPassword(String userId, String newPassword) {
        UserEntity user = getUser(userId);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
