package com.example.serveridoushu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "{\n" +
                "  \"message\": \"iDouShu 服务器运行中\",\n" +
                "  \"version\": \"1.0.0\",\n" +
                "  \"endpoints\": {\n" +
                "    \"用户相关\": [\n" +
                "      \"POST /api/users/register - 用户注册\",\n" +
                "      \"POST /api/users/login - 用户登录\",\n" +
                "      \"GET /api/users/profile - 获取个人资料\",\n" +
                "      \"PUT /api/users/profile - 更新个人资料\",\n" +
                "      \"PUT /api/users/settings - 更新设置\",\n" +
                "      \"GET /api/users/check-username - 检查用户名\",\n" +
                "      \"GET /api/users/check-email - 检查邮箱\"\n" +
                "    ],\n" +
                "    \"帖子相关\": [\n" +
                "      \"POST /api/posts/create - 创建帖子\",\n" +
                "      \"GET /api/posts/user/{userId} - 获取用户帖子\",\n" +
                "      \"GET /api/posts - 获取所有帖子\",\n" +
                "      \"GET /api/posts/{id} - 获取帖子详情\",\n" +
                "      \"PUT /api/posts/{id} - 更新帖子\",\n" +
                "      \"DELETE /api/posts/{id} - 删除帖子\",\n" +
                "      \"GET /api/posts/user/{userId}/stats - 帖子统计\"\n" +
                "    ],\n" +
                "    \"点赞相关\": [\n" +
                "      \"POST /api/likes - 点赞帖子\",\n" +
                "      \"DELETE /api/likes - 取消点赞\",\n" +
                "      \"GET /api/likes/user/{userId} - 用户点赞列表\",\n" +
                "      \"GET /api/likes/post/{postId} - 帖子点赞列表\",\n" +
                "      \"GET /api/likes/check - 检查点赞状态\",\n" +
                "      \"GET /api/likes/post/{postId}/count - 帖子点赞数\"\n" +
                "    ],\n" +
                "    \"关注相关\": [\n" +
                "      \"POST /api/follows - 关注用户\",\n" +
                "      \"DELETE /api/follows - 取消关注\",\n" +
                "      \"GET /api/follows/following/{userId} - 关注列表\",\n" +
                "      \"GET /api/follows/followers/{userId} - 粉丝列表\",\n" +
                "      \"GET /api/follows/check - 检查关注状态\",\n" +
                "      \"GET /api/follows/user/{userId}/stats - 关注统计\"\n" +
                "    ],\n" +
                "    \"评论相关\": [\n" +
                "      \"POST /api/comments - 发表评论\",\n" +
                "      \"DELETE /api/comments/{commentId} - 删除评论\",\n" +
                "      \"PUT /api/comments/{commentId} - 更新评论\",\n" +
                "      \"GET /api/comments/post/{postId} - 帖子评论列表\",\n" +
                "      \"GET /api/comments/user/{userId} - 用户评论列表\",\n" +
                "      \"GET /api/comments/post/{postId}/count - 评论数\"\n" +
                "    ]\n" +
                "  }\n" +
                "}";
    }
}