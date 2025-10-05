package com.example.serveridoushu.service;

import com.example.serveridoushu.model.Post;
import com.example.serveridoushu.model.User;
import com.example.serveridoushu.repository.PostRepository;
import com.example.serveridoushu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    // 发布帖子
    public Post createPost(Post post, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            post.setUser(user);

            // 验证帖子内容
            validatePostContent(post.getContent());

            if (post.getCreatedAt() == null) {
                post.setCreatedAt(LocalDateTime.now());
            }
            return postRepository.save(post);
        }
        throw new RuntimeException("用户不存在");
    }

    // 获取用户的所有帖子
    public List<Post> getUserPosts(Long userId) {
        return postRepository.findByUserId(userId);
    }

    // 获取所有帖子（按时间倒序）
    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    // 更新帖子
    public Post updatePost(Long id, Post postDetails) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();

            // 验证更新的内容
            if (postDetails.getContent() != null) {
                validatePostContent(postDetails.getContent());
                post.setContent(postDetails.getContent());
            }

            if (postDetails.getTitle() != null) {
                post.setTitle(postDetails.getTitle());
            }

            if (postDetails.getImageUrl() != null) {
                post.setImageUrl(postDetails.getImageUrl());
            }

            return postRepository.save(post);
        }
        throw new RuntimeException("帖子不存在");
    }

    // 内容合法性验证
    private void validatePostContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new RuntimeException("帖子内容不能为空");
        }
        if (content.length() > 5000) {
            throw new RuntimeException("帖子内容不能超过5000字符");
        }

        // 敏感词过滤（简单示例）
        String[] sensitiveWords = {"暴力", "色情", "赌博"};
        for (String word : sensitiveWords) {
            if (content.contains(word)) {
                throw new RuntimeException("帖子内容包含敏感词汇");
            }
        }
    }
}