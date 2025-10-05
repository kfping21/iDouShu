package com.example.serveridoushu.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "follows", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"follower_id", "followed_id"})
})
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower;  // 关注者

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followed_id", nullable = false)
    private User followed;  // 被关注者

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // 构造器
    public Follow() {
        this.createdAt = LocalDateTime.now();
    }

    public Follow(User follower, User followed) {
        this();
        this.follower = follower;
        this.followed = followed;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getFollower() { return follower; }
    public void setFollower(User follower) { this.follower = follower; }

    public User getFollowed() { return followed; }
    public void setFollowed(User followed) { this.followed = followed; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}