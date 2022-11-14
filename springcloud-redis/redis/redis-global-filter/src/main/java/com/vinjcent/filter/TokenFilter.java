package com.vinjcent.filter;


import com.vinjcent.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 针对所有的路由请求,一旦定义了就会投入使用
 */
@Configuration
public class TokenFilter implements GlobalFilter {

    private final RedisUtils redisUtils;

    @Autowired
    public TokenFilter(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String gatewayToken = redisUtils.get("gatewayToken", String.class);
        // 将gatewayToken保存至redis
        if (gatewayToken == null) {
            // 生成gatewayToken
            gatewayToken = UUID.randomUUID().toString();
            redisUtils.set("gatewayToken", gatewayToken);
            // 十分钟有效期
            redisUtils.expire("gatewayToken", 10, TimeUnit.MINUTES);
        }
        // 写入请求头
        ServerHttpRequest req = exchange.getRequest().mutate()
                .header("from", gatewayToken).build();
        return chain.filter(exchange.mutate().request(req.mutate().build()).build());
    }
}

