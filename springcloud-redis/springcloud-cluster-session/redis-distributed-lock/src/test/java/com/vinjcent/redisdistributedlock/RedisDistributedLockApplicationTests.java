package com.vinjcent.redisdistributedlock;

import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedisDistributedLockApplicationTests {

    @Autowired
    RedissonClient redissonClient;

    @Test
    void contextLoads() {

        RLock lock = redissonClient.getLock("lock:order:userId");
        // 参数: 获取锁的最大等待时间(期间会重试)、锁自动释放时间、时间单位
        lock.tryLock();
        lock.unlock();

    }

}
