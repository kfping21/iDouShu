package com.example.serveridoushu.repository;

import com.example.serveridoushu.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 根据帖子ID查找评论（按时间正序）
    @Query("SELECT c FROM Comment c WHERE c.post.id = :postId ORDER BY c.createdAt ASC")
    List<Comment> findByPostId(@Param("postId") Long postId);

    // 根据用户ID查找评论
    @Query("SELECT c FROM Comment c WHERE c.user.id = :userId ORDER BY c.createdAt DESC")
    List<Comment> findByUserId(@Param("userId") Long userId);

    // 统计帖子的评论数
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.post.id = :postId")
    Long countByPostId(@Param("postId") Long postId);

    // 查找用户对某个帖子的评论
    @Query("SELECT c FROM Comment c WHERE c.user.id = :userId AND c.post.id = :postId ORDER BY c.createdAt DESC")
    List<Comment> findByUserIdAndPostId(@Param("userId") Long userId, @Param("postId") Long postId);
}