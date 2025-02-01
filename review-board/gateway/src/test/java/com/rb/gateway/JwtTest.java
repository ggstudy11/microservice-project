package com.rb.gateway;

import com.rb.common.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.HashMap;
import java.util.Map;

/**
 * @author ：ggstudy11
 * @version :
 * @date ：Created in 2/1/2025 4:51 PM
 * @description：Jwt工具类测试
 * @modified By：
 */
@SpringBootTest
public class JwtTest {

    @Autowired
    private JwtUtil jwtUtil;


    @Test
    public void test() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", 123);
        claims.put("role", "ADMIN");
        String token = jwtUtil.generateToken(claims, "user@example.com");
    }
}
