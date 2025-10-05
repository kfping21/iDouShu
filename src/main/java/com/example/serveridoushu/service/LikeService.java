package com.example.serveridoushu.service;

import com.example.serveridoushu.model.Like;
import com.example.serveridoushu.model.Post;
import com.example.serveridoushu.model.User;
import com.example.serveridoushu.repository.LikeRepository;
import com.example.serveridoushu.repository.PostRepository;
import com.example.serveridoushu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    // 点赞
    public Like likePost(Long userId, Long postId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Post> postOptional = postRepository.findById(postId);

        if (userOptional.isPresent() && postOptional.isPresent()) {
            User user = userOptional.get();
            Post post = postOptional.get();

            // 检查是否已经点赞
            Optional<Like> existingLike = likeRepository.findByUserAndPost(user, post);
            if (existingLike.isPresent()) {
                throw new RuntimeException("已经点赞过该帖子");
            }

            Like like = new Like(user, post);
            return likeRepository.save(like);
        }
        throw new RuntimeException("用户或帖子不存在");
    }

    // 取消点赞
    public void unlikePost(Long userId, Long postId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Post> postOptional = postRepository.findById(postId);

        if (userOptional.isPresent() && postOptional.isPresent()) {
            User user = userOptional.get();
            Post post = postOptional.get();

            Optional<Like> likeOptional = likeRepository.findByUserAndPost(user, post);
            if (likeOptional.isPresent()) {
                likeRepository.delete(likeOptional.get());
            } else {
                throw new RuntimeException("尚未点赞该帖子");
            }
        } else {
            throw new RuntimeException("用户或帖子不存在");
        }
    }

    // 获取用户的点赞列表
    public List<Like> getUserLikes(Long userId) {
        return likeRepository.findByUserId(userId);
    }

    // 获取帖子的点赞列表
    public List<Like> getPostLikes(Long postId) {
        return likeRepository.findByPostId(postId);
    }

    // 获取帖子点赞数
    public Long getPostLikeCount(Long postId) {
        return likeRepository.countByPostId(postId);
    }

    // 检查用户是否点赞了帖子
    public Boolean isPostLikedByUser(Long userId, Long postId) {
        return likeRepository.existsByUserIdAndPostId(userId, postId);
    }
}