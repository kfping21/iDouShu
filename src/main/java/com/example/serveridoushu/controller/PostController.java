package com.example.serveridoushu.controller;

import com.example.serveridoushu.dto.ApiResponse;
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
    public ResponseEntity<ApiResponse<?>> createPost(@RequestBody Post post, @RequestParam Long userId) {
        try {
            Post createdPost = postService.createPost(post, userId);
            Map<String, Object> data = new HashMap<>();
            data.put("postId", createdPost.getId());
            data.put("userId", createdPost.getUserId());
            return ResponseEntity.ok(ApiResponse.success("帖子发布成功", data));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("发布失败: " + e.getMessage()));
        }
    }

    // 获取用户的所有帖子
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<?>> getUserPosts(@PathVariable Long userId) {
        List<Post> posts = postService.getUserPosts(userId);
        Map<String, Object> data = new HashMap<>();
        data.put("userId", userId);
        data.put("posts", posts);
        data.put("count", posts.size());
        return ResponseEntity.ok(ApiResponse.success("获取用户帖子成功", data));
    }

    // 获取所有帖子（首页时间线）
    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        Map<String, Object> data = new HashMap<>();
        data.put("posts", posts);
        data.put("total", posts.size());
        return ResponseEntity.ok(ApiResponse.success("获取所有帖子成功", data));
    }

    // 获取帖子详情
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        if (post != null) {
            return ResponseEntity.ok(ApiResponse.success("获取帖子详情成功", post));
        }
        return ResponseEntity.badRequest().body(ApiResponse.error("帖子不存在"));
    }

    // 删除帖子
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deletePost(@PathVariable Long id) {
        try {
            postService.deletePost(id);
            return ResponseEntity.ok(ApiResponse.success("帖子删除成功"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("删除失败: " + e.getMessage()));
        }
    }

    // 更新帖子
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updatePost(@PathVariable Long id, @RequestBody Post postDetails) {
        try {
            Post updatedPost = postService.updatePost(id, postDetails);
            return ResponseEntity.ok(ApiResponse.success("帖子更新成功", updatedPost));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("更新失败: " + e.getMessage()));
        }
    }

    // 获取帖子统计
    @GetMapping("/user/{userId}/stats")
    public ResponseEntity<ApiResponse<?>> getUserPostStats(@PathVariable Long userId) {
        List<Post> posts = postService.getUserPosts(userId);
        Map<String, Object> data = new HashMap<>();
        data.put("userId", userId);
        data.put("totalPosts", posts.size());
        data.put("lastPostDate", posts.isEmpty() ? null : posts.get(0).getCreatedAt());
        return ResponseEntity.ok(ApiResponse.success("获取帖子统计成功", data));
    }
}