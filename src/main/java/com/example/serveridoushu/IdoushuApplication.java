package com.example.serveridoushu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.example.serveridoushu.repository")
@EntityScan(basePackages = "com.example.serveridoushu.model")
public class IdoushuApplication {
    public static void main(String[] args) {
        SpringApplication.run(IdoushuApplication.class, args);
    }
}
