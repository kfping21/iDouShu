package com.example.serveridoushu.service;

import com.example.serveridoushu.model.Comment;
import com.example.serveridoushu.model.Post;
import com.example.serveridoushu.model.User;
import com.example.serveridoushu.repository.CommentRepository;
import com.example.serveridoushu.repository.PostRepository;
import com.example.serveridoushu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    // 发表评论
    public Comment createComment(Long userId, Long postId, String content) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Post> postOptional = postRepository.findById(postId);

        if (userOptional.isPresent() && postOptional.isPresent()) {
            User user = userOptional.get();
            Post post = postOptional.get();

            Comment comment = new Comment(content, user, post);
            return commentRepository.save(comment);
        }
        throw new RuntimeException("用户或帖子不存在");
    }

    // 删除评论
    public void deleteComment(Long commentId, Long userId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            // 只能删除自己的评论
            if (comment.getUser().getId().equals(userId)) {
                commentRepository.delete(comment);
            } else {
                throw new RuntimeException("无权删除该评论");
            }
        } else {
            throw new RuntimeException("评论不存在");
        }
    }

    // 获取帖子的评论列表
    public List<Comment> getPostComments(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    // 获取用户的评论列表
    public List<Comment> getUserComments(Long userId) {
        return commentRepository.findByUserId(userId);
    }

    // 获取评论数
    public Long getCommentCount(Long postId) {
        return commentRepository.countByPostId(postId);
    }

    // 更新评论
    public Comment updateComment(Long commentId, Long userId, String content) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            // 只能更新自己的评论
            if (comment.getUser().getId().equals(userId)) {
                comment.setContent(content);
                return commentRepository.save(comment);
            } else {
                throw new RuntimeException("无权更新该评论");
            }
        }
        throw new RuntimeException("评论不存在");
    }
}