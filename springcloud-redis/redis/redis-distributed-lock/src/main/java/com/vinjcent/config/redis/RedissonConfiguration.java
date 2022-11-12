package com.vinjcent.config.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redis分布式锁的配置类
 */
@Configuration
public class RedissonConfiguration {

    @Bean
    public RedissonClient redissonClient() {

        // 配置
        Config redissonConfig = new Config();

        redissonConfig.useSingleServer().setAddress("redis://192.168.159.100:6379");

        return Redisson.create();
    }
}
