package com.healthsync.user.service;

import com.healthsync.user.dto.OAuth2UserInfo;
import com.healthsync.user.repository.jpa.UserRepository;
import com.healthsync.user.repository.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class OAuth2UserService extends DefaultOAuth2UserService {

    private static final Logger logger = LoggerFactory.getLogger(OAuth2UserService.class);
    private final UserRepository userRepository;

    public OAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        logger.info("OAuth2 사용자 정보 로드 시작");
        logger.info("사용자 속성: {}", oauth2User.getAttributes());

        return processOAuth2User(userRequest, oauth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oauth2User) {
        OAuth2UserInfo oauth2UserInfo = new OAuth2UserInfo(oauth2User.getAttributes());

        String googleId = oauth2UserInfo.getId();

        logger.info("처리할 사용자 정보 - Google ID: {}", googleId);

        // 기존 사용자 확인만 수행 (생성은 Handler에서 처리)
        Optional<UserEntity> existingUser = userRepository.findByGoogleId(googleId);

        if (existingUser.isPresent()) {
            logger.info("기존 사용자 확인됨 - ID: {}", existingUser.get().getMemberSerialNumber());
        } else {
            logger.info("신규 사용자 - Handler에서 생성될 예정");
        }

        // role 정보 없이 기본 권한만 부여
        return new DefaultOAuth2User(
                Collections.singleton(() -> "ROLE_USER"),
                oauth2User.getAttributes(),
                "sub"
        );
    }
}