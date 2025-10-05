package com.example.serveridoushu.controller;

import com.example.serveridoushu.model.Like;
import com.example.serveridoushu.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    // 点赞帖子
    @PostMapping
    public ResponseEntity<?> likePost(@RequestParam Long userId, @RequestParam Long postId) {
        try {
            Like like = likeService.likePost(userId, postId);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "点赞成功");
            response.put("likeId", like.getId());
            response.put("userId", userId);
            response.put("postId", postId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // 取消点赞
    @DeleteMapping
    public ResponseEntity<?> unlikePost(@RequestParam Long userId, @RequestParam Long postId) {
        try {
            likeService.unlikePost(userId, postId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "取消点赞成功");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // 获取用户的点赞列表
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserLikes(@PathVariable Long userId) {
        List<Like> likes = likeService.getUserLikes(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("likes", likes);
        response.put("count", likes.size());
        return ResponseEntity.ok(response);
    }

    // 获取帖子的点赞列表
    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getPostLikes(@PathVariable Long postId) {
        List<Like> likes = likeService.getPostLikes(postId);
        Long likeCount = likeService.getPostLikeCount(postId);
        Map<String, Object> response = new HashMap<>();
        response.put("postId", postId);
        response.put("likes", likes);
        response.put("likeCount", likeCount);
        return ResponseEntity.ok(response);
    }

    // 检查用户是否点赞了帖子
    @GetMapping("/check")
    public ResponseEntity<?> checkLike(@RequestParam Long userId, @RequestParam Long postId) {
        Boolean isLiked = likeService.isPostLikedByUser(userId, postId);
        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("postId", postId);
        response.put("isLiked", isLiked);
        return ResponseEntity.ok(response);
    }

    // 获取帖子点赞数
    @GetMapping("/post/{postId}/count")
    public ResponseEntity<?> getPostLikeCount(@PathVariable Long postId) {
        Long likeCount = likeService.getPostLikeCount(postId);
        Map<String, Object> response = new HashMap<>();
        response.put("postId", postId);
        response.put("likeCount", likeCount);
        return ResponseEntity.ok(response);
    }
}