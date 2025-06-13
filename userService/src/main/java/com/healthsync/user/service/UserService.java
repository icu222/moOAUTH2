package com.healthsync.user.service;

import com.healthsync.user.domain.User;

import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Optional<User> findByGoogleId(String googleId);
    Optional<User> findById(Long memberSerialNumber);
    User updateUser(User user);
    void updateLastLoginAt(Long memberSerialNumber);
    boolean existsByGoogleId(String googleId);
    // generateMemberSerialNumber() 메서드 제거
}