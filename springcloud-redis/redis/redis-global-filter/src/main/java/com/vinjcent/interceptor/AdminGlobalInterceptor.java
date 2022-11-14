package com.vinjcent.interceptor;

import com.vinjcent.utils.RedisUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 服务端请求拦截(用于防止越过网关直接访问)
 */
@Configuration
public class AdminGlobalInterceptor implements HandlerInterceptor {

    private final RedisUtils redisUtils;

    public AdminGlobalInterceptor(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
        // 获取当前请求头from信息
        String secretKey = request.getHeader("from");
        // 获取gatewayToken
        String gatewayToken = redisUtils.get("gatewayToken", String.class);

        if(secretKey == null || !secretKey.equals(gatewayToken)) {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write("非法访问！");
            return false;
        }
        return true;
    }
}

