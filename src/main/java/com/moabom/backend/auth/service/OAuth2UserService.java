package com.moabom.backend.auth.service;

import com.moabom.backend.auth.model.UserEntity;
import com.moabom.backend.auth.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final AuthRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 구글에서 사용자 정보(attributes) 받아오기
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 구글 사용자 정보에서 필요한 값 추출
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        String picture = (String) attributes.get("picture");

        // DB에 유저가 있으면 업데이트, 없으면 새로 저장
        UserEntity user = userRepository.findByUserId(email)
                .map(entity -> {
                    entity.setNickName(name);
                    entity.setUserImage(picture);
                    entity.setEnabled(1);
                    entity.setRole("ROLE_USER");
                    return entity;
                })
                .orElse(UserEntity.builder()
                        .userId(email)
                        .password("") // 소셜로그인 사용자는 패스워드 비워둠
                        .nickName(name)
                        .userImage(picture)
                        .enabled(1)
                        .role("ROLE_USER")
                        .build());

        userRepository.save(user);

        // OAuth2User 반환
        return oAuth2User;
    }
}
