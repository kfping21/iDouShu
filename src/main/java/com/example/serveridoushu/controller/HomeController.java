// HomeController.java
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
                "      \"PUT /api/users/settings - 更新设置\"\n" +
                "    ],\n" +
                "    \"帖子相关\": [\n" +
                "      \"POST /api/posts/create - 创建帖子\",\n" +
                "      \"GET /api/posts/user/{userId} - 获取用户帖子\",\n" +
                "      \"GET /api/posts - 获取所有帖子\",\n" +
                "      \"GET /api/posts/{id} - 获取帖子详情\",\n" +
                "      \"DELETE /api/posts/{id} - 删除帖子\"\n" +
                "    ]\n" +
                "  }\n" +
                "}";
    }
}