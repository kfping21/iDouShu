package com.example.serveridoushu.service;

import com.example.serveridoushu.model.Follow;
import com.example.serveridoushu.model.User;
import com.example.serveridoushu.repository.FollowRepository;
import com.example.serveridoushu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FollowService {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    // 关注用户
    public Follow followUser(Long followerId, Long followedId) {
        if (followerId.equals(followedId)) {
            throw new RuntimeException("不能关注自己");
        }

        Optional<User> followerOptional = userRepository.findById(followerId);
        Optional<User> followedOptional = userRepository.findById(followedId);

        if (followerOptional.isPresent() && followedOptional.isPresent()) {
            User follower = followerOptional.get();
            User followed = followedOptional.get();

            // 检查是否已经关注
            Optional<Follow> existingFollow = followRepository.findByFollowerAndFollowed(follower, followed);
            if (existingFollow.isPresent()) {
                throw new RuntimeException("已经关注该用户");
            }

            Follow follow = new Follow(follower, followed);
            return followRepository.save(follow);
        }
        throw new RuntimeException("用户不存在");
    }

    // 取消关注
    public void unfollowUser(Long followerId, Long followedId) {
        Optional<User> followerOptional = userRepository.findById(followerId);
        Optional<User> followedOptional = userRepository.findById(followedId);

        if (followerOptional.isPresent() && followedOptional.isPresent()) {
            User follower = followerOptional.get();
            User followed = followedOptional.get();

            Optional<Follow> followOptional = followRepository.findByFollowerAndFollowed(follower, followed);
            if (followOptional.isPresent()) {
                followRepository.delete(followOptional.get());
            } else {
                throw new RuntimeException("尚未关注该用户");
            }
        } else {
            throw new RuntimeException("用户不存在");
        }
    }

    // 获取用户的关注列表
    public List<Follow> getUserFollowing(Long userId) {
        return followRepository.findByFollowerId(userId);
    }

    // 获取用户的粉丝列表
    public List<Follow> getUserFollowers(Long userId) {
        return followRepository.findByFollowedId(userId);
    }

    // 获取关注数
    public Long getFollowingCount(Long userId) {
        return followRepository.countByFollowerId(userId);
    }

    // 获取粉丝数
    public Long getFollowersCount(Long userId) {
        return followRepository.countByFollowedId(userId);
    }

    // 检查是否已关注
    public Boolean isFollowing(Long followerId, Long followedId) {
        return followRepository.existsByFollowerIdAndFollowedId(followerId, followedId);
    }
}