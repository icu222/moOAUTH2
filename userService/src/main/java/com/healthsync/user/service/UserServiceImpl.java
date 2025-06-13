package com.healthsync.user.service;

import com.healthsync.user.domain.User;
import com.healthsync.user.repository.entity.UserEntity;
import com.healthsync.user.repository.jpa.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {
        UserEntity entity = UserEntity.fromDomain(user);
        UserEntity savedEntity = userRepository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByGoogleId(String googleId) {
        return userRepository.findByGoogleId(googleId)
                .map(UserEntity::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long memberSerialNumber) {
        return userRepository.findById(memberSerialNumber)
                .map(UserEntity::toDomain);
    }

    @Override
    public User updateUser(User user) {
        user.setUpdatedAt(LocalDateTime.now());
        UserEntity entity = UserEntity.fromDomain(user);
        UserEntity savedEntity = userRepository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    public void updateLastLoginAt(Long memberSerialNumber) {
        LocalDateTime now = LocalDateTime.now();
        userRepository.updateLastLoginAt(memberSerialNumber, now, now);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByGoogleId(String googleId) {
        return userRepository.existsByGoogleId(googleId);
    }
}