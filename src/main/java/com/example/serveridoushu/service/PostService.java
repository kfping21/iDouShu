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
            post.setTitle(postDetails.getTitle());
            post.setContent(postDetails.getContent());
            post.setImageUrl(postDetails.getImageUrl());
            return postRepository.save(post);
        }
        return null;
    }
}