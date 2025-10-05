package com.example.serveridoushu.controller;

import com.example.serveridoushu.dto.UpdateProfileRequest;
import com.example.serveridoushu.dto.UpdateSettingsRequest;
import com.example.serveridoushu.model.User;
import com.example.serveridoushu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 用户注册
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User registeredUser = userService.register(user);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "用户注册成功");
            response.put("userId", registeredUser.getId());
            response.put("username", registeredUser.getUsername());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // 用户登录
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        User user = userService.login(username, password);
        if (user != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "登录成功");
            response.put("userId", user.getId());
            response.put("username", user.getUsername());
            response.put("avatarUrl", user.getAvatarUrl());
            response.put("nickName", user.getNickName());
            return ResponseEntity.ok(response);
        }

        Map<String, String> error = new HashMap<>();
        error.put("error", "用户名或密码错误");
        return ResponseEntity.badRequest().body(error);
    }

    // 获取用户资料
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestParam Long userId) {
        User user = userService.getUserProfile(userId);
        if (user != null) {
            // 不返回密码等敏感信息
            user.setPassword(null);
            return ResponseEntity.ok(user);
        }

        Map<String, String> error = new HashMap<>();
        error.put("error", "用户不存在");
        return ResponseEntity.badRequest().body(error);
    }

    // 更新用户资料
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestParam Long userId, @RequestBody UpdateProfileRequest profileRequest) {
        User updatedUser = userService.updateUserProfile(userId, profileRequest);
        if (updatedUser != null) {
            updatedUser.setPassword(null); // 不返回密码
            Map<String, Object> response = new HashMap<>();
            response.put("message", "资料更新成功");
            response.put("user", updatedUser);
            return ResponseEntity.ok(response);
        }

        Map<String, String> error = new HashMap<>();
        error.put("error", "更新失败，用户不存在");
        return ResponseEntity.badRequest().body(error);
    }

    // 更新用户设置
    @PutMapping("/settings")
    public ResponseEntity<?> updateSettings(@RequestParam Long userId, @RequestBody UpdateSettingsRequest settingsRequest) {
        User updatedUser = userService.updateUserSettings(userId, settingsRequest);
        if (updatedUser != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "设置更新成功");
            response.put("isPrivate", updatedUser.getIsPrivate());
            response.put("notificationEnabled", updatedUser.getNotificationEnabled());
            return ResponseEntity.ok(response);
        }

        Map<String, String> error = new HashMap<>();
        error.put("error", "更新失败，用户不存在");
        return ResponseEntity.badRequest().body(error);
    }

    // 检查用户名是否存在
    @GetMapping("/check-username")
    public ResponseEntity<?> checkUsername(@RequestParam String username) {
        boolean exists = userService.isUsernameExists(username);
        Map<String, Object> response = new HashMap<>();
        response.put("username", username);
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

    // 检查邮箱是否存在
    @GetMapping("/check-email")
    public ResponseEntity<?> checkEmail(@RequestParam String email) {
        boolean exists = userService.isEmailExists(email);
        Map<String, Object> response = new HashMap<>();
        response.put("email", email);
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }
}