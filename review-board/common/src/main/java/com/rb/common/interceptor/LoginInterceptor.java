package com.rb.common.interceptor;

import com.rb.common.context.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author ：ggstudy11
 * @version :
 * @date ：Created in 2/3/2025 6:14 AM
 * @description：登录鉴权拦截器
 * @modified By：
 */

public class LoginInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        String userInfo = request.getHeader("user-info");
        if (userInfo != null) {
            UserContext.setUser(Long.valueOf(userInfo));
        }
        return true;
    }

    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) throws Exception {
        UserContext.removeUser();
    }
}
