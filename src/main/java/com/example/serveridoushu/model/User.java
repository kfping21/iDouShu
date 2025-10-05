package com.example.serveridoushu.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(unique = true, length = 100)
    private String email;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "nick_name")
    private String nickName;

    private String bio;

    private String gender;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "is_private")
    private Boolean isPrivate = false;

    @Column(name = "notification_enabled")
    private Boolean notificationEnabled = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 用户发布的帖子（一对多关系）
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    // 用户点赞的帖子（多对多关系）
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Like> likes = new ArrayList<>();

    // 用户发表的评论（一对多关系）
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    // 用户关注的其他人（作为关注者）
    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Follow> following = new ArrayList<>();

    // 关注该用户的人（作为被关注者）
    @OneToMany(mappedBy = "followed", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Follow> followers = new ArrayList<>();

    // 构造器
    public User() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public User(String username, String password, String email, String avatarUrl, String nickName) {
        this();
        this.username = username;
        this.password = password;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.nickName = nickName;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    public String getNickName() { return nickName; }
    public void setNickName(String nickName) { this.nickName = nickName; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getBirthDate() { return birthDate; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public Boolean getIsPrivate() { return isPrivate; }
    public void setIsPrivate(Boolean isPrivate) { this.isPrivate = isPrivate; }

    public Boolean getNotificationEnabled() { return notificationEnabled; }
    public void setNotificationEnabled(Boolean notificationEnabled) { this.notificationEnabled = notificationEnabled; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public List<Post> getPosts() { return posts; }
    public void setPosts(List<Post> posts) { this.posts = posts; }

    public List<Like> getLikes() { return likes; }
    public void setLikes(List<Like> likes) { this.likes = likes; }

    public List<Comment> getComments() { return comments; }
    public void setComments(List<Comment> comments) { this.comments = comments; }

    public List<Follow> getFollowing() { return following; }
    public void setFollowing(List<Follow> following) { this.following = following; }

    public List<Follow> getFollowers() { return followers; }
    public void setFollowers(List<Follow> followers) { this.followers = followers; }

    // 辅助方法
    public void addPost(Post post) {
        posts.add(post);
        post.setUser(this);
    }

    public void removePost(Post post) {
        posts.remove(post);
        post.setUser(null);
    }

    public void addLike(Like like) {
        likes.add(like);
        like.setUser(this);
    }

    public void removeLike(Like like) {
        likes.remove(like);
        like.setUser(null);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setUser(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setUser(null);
    }

    public void addFollowing(Follow follow) {
        following.add(follow);
        follow.setFollower(this);
    }

    public void removeFollowing(Follow follow) {
        following.remove(follow);
        follow.setFollower(null);
    }

    public void addFollower(Follow follow) {
        followers.add(follow);
        follow.setFollowed(this);
    }

    public void removeFollower(Follow follow) {
        followers.remove(follow);
        follow.setFollowed(null);
    }

    // 获取帖子数
    public int getPostCount() {
        return posts != null ? posts.size() : 0;
    }

    // 获取关注数
    public int getFollowingCount() {
        return following != null ? following.size() : 0;
    }

    // 获取粉丝数
    public int getFollowersCount() {
        return followers != null ? followers.size() : 0;
    }
}