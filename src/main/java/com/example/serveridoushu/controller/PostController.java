package com.example.serveridoushu.controller;

import com.example.serveridoushu.model.Post;
import com.example.serveridoushu.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    // 发布帖子
    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestBody Post post, @RequestParam Long userId) {
        try {
            Post createdPost = postService.createPost(post, userId);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "帖子发布成功");
            response.put("postId", createdPost.getId());
            response.put("userId", createdPost.getUserId());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "发布失败: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // 获取用户的所有帖子
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserPosts(@PathVariable Long userId) {
        List<Post> posts = postService.getUserPosts(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("posts", posts);
        response.put("count", posts.size());
        return ResponseEntity.ok(response);
    }

    // 获取所有帖子（首页时间线）
    @GetMapping
    public ResponseEntity<?> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        Map<String, Object> response = new HashMap<>();
        response.put("posts", posts);
        response.put("total", posts.size());
        return ResponseEntity.ok(response);
    }

    // 获取帖子详情
    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        if (post != null) {
            return ResponseEntity.ok(post);
        }

        Map<String, String> error = new HashMap<>();
        error.put("error", "帖子不存在");
        return ResponseEntity.badRequest().body(error);
    }

    // 删除帖子
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        try {
            postService.deletePost(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "帖子删除成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "删除失败: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // 获取帖子统计
    @GetMapping("/user/{userId}/stats")
    public ResponseEntity<?> getUserPostStats(@PathVariable Long userId) {
        List<Post> posts = postService.getUserPosts(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("totalPosts", posts.size());
        response.put("lastPostDate", posts.isEmpty() ? null : posts.get(0).getCreatedAt());
        return ResponseEntity.ok(response);
    }

    // 更新帖子
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody Post postDetails) {
        Post updatedPost = postService.updatePost(id, postDetails);
        if (updatedPost != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "帖子更新成功");
            response.put("post", updatedPost);
            return ResponseEntity.ok(response);
        }

        Map<String, String> error = new HashMap<>();
        error.put("error", "更新失败，帖子不存在");
        return ResponseEntity.badRequest().body(error);
    }
}