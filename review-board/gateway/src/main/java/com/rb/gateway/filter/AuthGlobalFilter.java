package com.rb.gateway.filter;

import com.rb.common.utils.JwtUtil;
import com.rb.gateway.config.AuthConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：ggstudy11
 * @version :  1.0
 * @date ：Created in 2024-11-28 21:26
 * @description：登录鉴权过滤器
 * @modified By：
 */
@Component
@RequiredArgsConstructor
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;
    private final AuthConfig authConfig;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取请求头
        ServerHttpRequest request = exchange.getRequest();
        // 放行路径
        if (isExclude(request.getPath().toString())) {
            return chain.filter(exchange);
        }
        String token = request.getHeaders().getFirst("Authorization");
        if (!jwtUtil.validateToken(token)) {
            ServerHttpResponse response = exchange.getResponse();
            response.setRawStatusCode(401);
            return response.setComplete();
        }
        Map<String, Object>  claims = jwtUtil.parseToken(token);
        String userInfo = claims.get("userId").toString();
        ServerWebExchange swx = exchange.mutate()
                .request(b ->b.header("user-info", userInfo))
                .build();
        return chain.filter(swx);
    }

    /**
    * @Description  是否放行路径
    * @param antPath -放行路径
    * @return
    * @Author ggstudy11
    * @Date
    */
    private boolean isExclude(String antPath) {
        for (String pathPattern : authConfig.getExcludePaths()) {
            if (antPathMatcher.match(pathPattern, antPath)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
