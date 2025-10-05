// UserRepository.java - 更新版本
package com.example.serveridoushu.repository;

import com.example.serveridoushu.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);  // 根据用户名查找用户
    User findByEmail(String email);        // 根据邮箱查找用户
}