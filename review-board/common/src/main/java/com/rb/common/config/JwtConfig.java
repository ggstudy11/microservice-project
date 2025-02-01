package com.rb.common.config;

import com.rb.common.utils.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author ：ggstudy11
 * @version :
 * @date ：Created in 2/1/2025 4:55 PM
 * @description：Jwt自动装配
 * @modified By：
 */
@Configuration
public class JwtConfig {

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }
}
