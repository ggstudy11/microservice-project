package com.rb.common.context;

/**
 * @author ：ggstudy11
 * @version :
 * @date ：Created in 2/3/2025 6:15 AM
 * @description：用户ThreadLocal
 * @modified By：
 */
public class UserContext {
    private static final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * @Description  存入用户id
     * @param userId
     * @return
     * @Author ggstudy11
     * @Date
     */
    public static void setUser(Long userId) {
        threadLocal.set(userId);
    }

    /**
     * @Description 取用户id
     * @param
     * @return
     * @Author ggstudy11
     * @Date
     */
    public static Long getUser() {
        return threadLocal.get();
    }

    /**
     * @Description 清空，解决内存问题
     * @param
     * @return
     * @Author ggstudy11
     * @Date
     */
    public static void removeUser() {
        threadLocal.remove();
    }
}
