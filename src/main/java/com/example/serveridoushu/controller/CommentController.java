package com.example.serveridoushu.controller;

import com.example.serveridoushu.model.Comment;
import com.example.serveridoushu.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // 发表评论
    @PostMapping
    public ResponseEntity<?> createComment(@RequestParam Long userId, @RequestParam Long postId, @RequestParam String content) {
        try {
            Comment comment = commentService.createComment(userId, postId, content);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "评论成功");
            response.put("commentId", comment.getId());
            response.put("userId", userId);
            response.put("postId", postId);
            response.put("content", content);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // 删除评论
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId, @RequestParam Long userId) {
        try {
            commentService.deleteComment(commentId, userId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "删除评论成功");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // 获取帖子的评论列表
    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getPostComments(@PathVariable Long postId) {
        List<Comment> comments = commentService.getPostComments(postId);
        Long commentCount = commentService.getCommentCount(postId);
        Map<String, Object> response = new HashMap<>();
        response.put("postId", postId);
        response.put("comments", comments);
        response.put("commentCount", commentCount);
        return ResponseEntity.ok(response);
    }

    // 获取用户的评论列表
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserComments(@PathVariable Long userId) {
        List<Comment> comments = commentService.getUserComments(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("comments", comments);
        response.put("count", comments.size());
        return ResponseEntity.ok(response);
    }

    // 更新评论
    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestParam Long userId, @RequestParam String content) {
        try {
            Comment updatedComment = commentService.updateComment(commentId, userId, content);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "更新评论成功");
            response.put("comment", updatedComment);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // 获取评论数
    @GetMapping("/post/{postId}/count")
    public ResponseEntity<?> getCommentCount(@PathVariable Long postId) {
        Long commentCount = commentService.getCommentCount(postId);
        Map<String, Object> response = new HashMap<>();
        response.put("postId", postId);
        response.put("commentCount", commentCount);
        return ResponseEntity.ok(response);
    }
}