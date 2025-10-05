package com.example.serveridoushu.repository;

import com.example.serveridoushu.model.Follow;
import com.example.serveridoushu.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    // 根据关注者和被关注者查找关注关系
    Optional<Follow> findByFollowerAndFollowed(User follower, User followed);

    // 查找用户的关注列表
    @Query("SELECT f FROM Follow f WHERE f.follower.id = :followerId ORDER BY f.createdAt DESC")
    List<Follow> findByFollowerId(@Param("followerId") Long followerId);

    // 查找用户的粉丝列表
    @Query("SELECT f FROM Follow f WHERE f.followed.id = :followedId ORDER BY f.createdAt DESC")
    List<Follow> findByFollowedId(@Param("followedId") Long followedId);

    // 统计关注数
    @Query("SELECT COUNT(f) FROM Follow f WHERE f.follower.id = :userId")
    Long countByFollowerId(@Param("userId") Long userId);

    // 统计粉丝数
    @Query("SELECT COUNT(f) FROM Follow f WHERE f.followed.id = :userId")
    Long countByFollowedId(@Param("userId") Long userId);

    // 检查是否已关注
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Follow f WHERE f.follower.id = :followerId AND f.followed.id = :followedId")
    Boolean existsByFollowerIdAndFollowedId(@Param("followerId") Long followerId, @Param("followedId") Long followedId);
}