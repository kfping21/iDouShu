package com.example.serveridoushu.repository;

import com.example.serveridoushu.model.Like;
import com.example.serveridoushu.model.Post;
import com.example.serveridoushu.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    // 根据用户和帖子查找点赞
    Optional<Like> findByUserAndPost(User user, Post post);

    // 根据用户ID查找点赞
    @Query("SELECT l FROM Like l WHERE l.user.id = :userId ORDER BY l.createdAt DESC")
    List<Like> findByUserId(@Param("userId") Long userId);

    // 根据帖子ID查找点赞
    @Query("SELECT l FROM Like l WHERE l.post.id = :postId ORDER BY l.createdAt DESC")
    List<Like> findByPostId(@Param("postId") Long postId);

    // 统计帖子的点赞数
    @Query("SELECT COUNT(l) FROM Like l WHERE l.post.id = :postId")
    Long countByPostId(@Param("postId") Long postId);

    // 检查用户是否点赞了某个帖子
    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM Like l WHERE l.user.id = :userId AND l.post.id = :postId")
    Boolean existsByUserIdAndPostId(@Param("userId") Long userId, @Param("postId") Long postId);
}