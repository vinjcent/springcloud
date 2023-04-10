package com.vinjcent.api.domain;

/**
 * @author vinjcent
 * @description 本地线程数据
 * @since 2023/3/31 14:56
 */
public class ThreadLocalData {

    /**
     * 该本地线程数据用于存储用户token信息
     */
    public static final ThreadLocal<String> USER_TOKEN = new ThreadLocal<>();

    /**
     * 获取当前用户token
     *
     * @return 用户token
     */
    public static String getToken() {
        return USER_TOKEN.get();
    }

    /**
     * 设置当前用户token
     *
     * @param token 用户token
     */
    public static void setToken(String token) {
        USER_TOKEN.set(token);
    }

    /**
     * 移除当前用户token
     */
    public static void remove() {
        USER_TOKEN.remove();
    }

}
