package com.rb.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ：ggstudy11
 * @version :
 * @date ：Created in 1/29/2025 4:22 PM
 * @description：用户启动类
 * @modified By：
 */

@SpringBootApplication
@MapperScan("com.rb.user.mapper")
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
