package com.example.serveridoushu.service;

import com.example.serveridoushu.dto.UpdateProfileRequest;
import com.example.serveridoushu.dto.UpdateSettingsRequest;
import com.example.serveridoushu.model.User;
import com.example.serveridoushu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 注册用户
    public User register(User user) {
        // 检查用户名是否已存在
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("用户名已存在");
        }

        // 检查邮箱是否已存在
        if (user.getEmail() != null && userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("邮箱已被注册");
        }

        // 验证密码强度
        validatePassword(user.getPassword());

        // 设置默认昵称
        if (user.getNickName() == null) {
            user.setNickName(user.getUsername());
        }

        return userRepository.save(user);
    }

    // 用户登录
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    // 获取用户资料
    public User getUserProfile(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    // 更新用户资料
    public User updateUserProfile(Long userId, UpdateProfileRequest profileRequest) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (profileRequest.getNickName() != null) {
                user.setNickName(profileRequest.getNickName());
            }
            if (profileRequest.getBio() != null) {
                user.setBio(profileRequest.getBio());
            }
            if (profileRequest.getGender() != null) {
                user.setGender(profileRequest.getGender());
            }
            if (profileRequest.getBirthDate() != null) {
                user.setBirthDate(profileRequest.getBirthDate());
            }
            if (profileRequest.getPhoneNumber() != null) {
                user.setPhoneNumber(profileRequest.getPhoneNumber());
            }
            if (profileRequest.getAvatarUrl() != null) {
                user.setAvatarUrl(profileRequest.getAvatarUrl());
            }

            user.setUpdatedAt(LocalDateTime.now());
            return userRepository.save(user);
        }
        return null;
    }

    // 更新用户设置
    public User updateUserSettings(Long userId, UpdateSettingsRequest settingsRequest) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (settingsRequest.getIsPrivate() != null) {
                user.setIsPrivate(settingsRequest.getIsPrivate());
            }
            if (settingsRequest.getNotificationEnabled() != null) {
                user.setNotificationEnabled(settingsRequest.getNotificationEnabled());
            }

            user.setUpdatedAt(LocalDateTime.now());
            return userRepository.save(user);
        }
        return null;
    }

    // 检查用户名是否存在
    public boolean isUsernameExists(String username) {
        return userRepository.findByUsername(username) != null;
    }

    // 检查邮箱是否存在
    public boolean isEmailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    // 密码强度验证
    private void validatePassword(String password) {
        if (password == null || password.length() < 6) {
            throw new RuntimeException("密码长度至少6位");
        }
        // 检查是否包含字母和数字
        boolean hasLetter = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            }
        }

        if (!hasLetter || !hasDigit) {
            throw new RuntimeException("密码必须包含字母和数字");
        }
    }
}