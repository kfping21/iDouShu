package com.example.serveridoushu.controller;

import com.example.serveridoushu.dto.ApiResponse;
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
    public ResponseEntity<ApiResponse<?>> createComment(@RequestParam Long userId, @RequestParam Long postId, @RequestParam String content) {
        try {
            Comment comment = commentService.createComment(userId, postId, content);
            Map<String, Object> data = new HashMap<>();
            data.put("commentId", comment.getId());
            data.put("userId", userId);
            data.put("postId", postId);
            data.put("content", content);
            return ResponseEntity.ok(ApiResponse.success("评论成功", data));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    // 删除评论
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<?>> deleteComment(@PathVariable Long commentId, @RequestParam Long userId) {
        try {
            commentService.deleteComment(commentId, userId);
            return ResponseEntity.ok(ApiResponse.success("删除评论成功"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    // 获取帖子的评论列表
    @GetMapping("/post/{postId}")
    public ResponseEntity<ApiResponse<?>> getPostComments(@PathVariable Long postId) {
        List<Comment> comments = commentService.getPostComments(postId);
        Long commentCount = commentService.getCommentCount(postId);
        Map<String, Object> data = new HashMap<>();
        data.put("postId", postId);
        data.put("comments", comments);
        data.put("commentCount", commentCount);
        return ResponseEntity.ok(ApiResponse.success("获取评论列表成功", data));
    }

    // 获取用户的评论列表
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<?>> getUserComments(@PathVariable Long userId) {
        List<Comment> comments = commentService.getUserComments(userId);
        Map<String, Object> data = new HashMap<>();
        data.put("userId", userId);
        data.put("comments", comments);
        data.put("count", comments.size());
        return ResponseEntity.ok(ApiResponse.success("获取用户评论成功", data));
    }

    // 更新评论
    @PutMapping("/{commentId}")
    public ResponseEntity<ApiResponse<?>> updateComment(@PathVariable Long commentId, @RequestParam Long userId, @RequestParam String content) {
        try {
            Comment updatedComment = commentService.updateComment(commentId, userId, content);
            return ResponseEntity.ok(ApiResponse.success("更新评论成功", updatedComment));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    // 获取评论数
    @GetMapping("/post/{postId}/count")
    public ResponseEntity<ApiResponse<?>> getCommentCount(@PathVariable Long postId) {
        Long commentCount = commentService.getCommentCount(postId);
        Map<String, Object> data = new HashMap<>();
        data.put("postId", postId);
        data.put("commentCount", commentCount);
        return ResponseEntity.ok(ApiResponse.success("获取评论数成功", data));
    }
}