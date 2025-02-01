package com.rb.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author ：ggstudy11
 * @version :
 * @date ：Created in 2/1/2025 5:42 PM
 * @description：登录鉴权配置
 * @modified By：
 */
@Configuration
@ConfigurationProperties(prefix = "rb.auth")
@Data
public class AuthConfig {
    List<String> excludePaths;
}
