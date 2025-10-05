package com.example.serveridoushu.repository;

import com.example.serveridoushu.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 根据用户ID查找帖子
    @Query("SELECT p FROM Post p WHERE p.user.id = :userId ORDER BY p.createdAt DESC")
    List<Post> findByUserId(@Param("userId") Long userId);

    // 查找所有帖子并按时间倒序排列
    List<Post> findAllByOrderByCreatedAtDesc();
}