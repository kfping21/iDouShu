package com.example.serveridoushu.repository;

import com.example.serveridoushu.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long userId);  // 根据用户ID查找帖子的集合
}
