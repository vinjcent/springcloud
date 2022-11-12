package com.vinjcent.service;

import com.vinjcent.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class LuaServiceImpl {

    private final RedisUtils redisUtils;

    public static final DefaultRedisScript<Long> SECKILL_SCRIPT;

    static {
        SECKILL_SCRIPT = new DefaultRedisScript<>();
        SECKILL_SCRIPT.setLocation(new ClassPathResource("seckill.lua"));
        SECKILL_SCRIPT.setResultType(Long.class);
    }

    @Autowired
    public LuaServiceImpl(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    public void loadTheLua() {

        // 执行脚本
        Long result = redisUtils.execute(SECKILL_SCRIPT, Collections.emptyList(), "voucherId", "userId");

        if (result != 0L) {
            // 条件判断
        }

        // 有购买资格
        // TODO 保存到阻塞队列

    }

}
