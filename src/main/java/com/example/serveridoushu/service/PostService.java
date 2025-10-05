package com.example.serveridoushu.service;

import com.example.serveridoushu.model.Post;
import com.example.serveridoushu.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    // 发布帖子
    public Post createPost(Post post) {
        // 确保设置创建时间
        if (post.getCreatedAt() == null) {
            post.setCreatedAt(LocalDateTime.now());
        }
        return postRepository.save(post);
    }

    // 获取用户的所有帖子
    public List<Post> getUserPosts(Long userId) {
        return postRepository.findByUserId(userId);
    }

    // 添加其他可能用到的方法
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}