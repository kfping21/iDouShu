package com.example.serveridoushu.controller;

import com.example.serveridoushu.model.Follow;
import com.example.serveridoushu.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/follows")
public class FollowController {

    @Autowired
    private FollowService followService;

    // 关注用户
    @PostMapping
    public ResponseEntity<?> followUser(@RequestParam Long followerId, @RequestParam Long followedId) {
        try {
            Follow follow = followService.followUser(followerId, followedId);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "关注成功");
            response.put("followId", follow.getId());
            response.put("followerId", followerId);
            response.put("followedId", followedId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // 取消关注
    @DeleteMapping
    public ResponseEntity<?> unfollowUser(@RequestParam Long followerId, @RequestParam Long followedId) {
        try {
            followService.unfollowUser(followerId, followedId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "取消关注成功");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // 获取用户的关注列表
    @GetMapping("/following/{userId}")
    public ResponseEntity<?> getUserFollowing(@PathVariable Long userId) {
        List<Follow> following = followService.getUserFollowing(userId);
        Long followingCount = followService.getFollowingCount(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("following", following);
        response.put("followingCount", followingCount);
        return ResponseEntity.ok(response);
    }

    // 获取用户的粉丝列表
    @GetMapping("/followers/{userId}")
    public ResponseEntity<?> getUserFollowers(@PathVariable Long userId) {
        List<Follow> followers = followService.getUserFollowers(userId);
        Long followersCount = followService.getFollowersCount(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("followers", followers);
        response.put("followersCount", followersCount);
        return ResponseEntity.ok(response);
    }

    // 检查是否已关注
    @GetMapping("/check")
    public ResponseEntity<?> checkFollow(@RequestParam Long followerId, @RequestParam Long followedId) {
        Boolean isFollowing = followService.isFollowing(followerId, followedId);
        Map<String, Object> response = new HashMap<>();
        response.put("followerId", followerId);
        response.put("followedId", followedId);
        response.put("isFollowing", isFollowing);
        return ResponseEntity.ok(response);
    }

    // 获取用户统计
    @GetMapping("/user/{userId}/stats")
    public ResponseEntity<?> getUserStats(@PathVariable Long userId) {
        Long followingCount = followService.getFollowingCount(userId);
        Long followersCount = followService.getFollowersCount(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("followingCount", followingCount);
        response.put("followersCount", followersCount);
        return ResponseEntity.ok(response);
    }
}