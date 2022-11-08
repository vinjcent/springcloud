package com.vinjcent.service.impl;

import com.vinjcent.service.Lock;
import com.vinjcent.utils.RedisUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * redis简单的分布式锁
 */
public class SimpleRedisLock implements Lock {

    private String name;    // module:user
    private RedisUtils redisUtils;

    public SimpleRedisLock(String name, RedisUtils redisUtils) {
        this.name = name;
        this.redisUtils = redisUtils;
    }

    public static final String KEY_PREFIX = "lock:";

    public static final String ID_PREFIX = UUID.randomUUID().toString() + "-";


    @Override
    public boolean tryLock(long timeout) {
        // 获取线程标识(线程id)
        String threadId = ID_PREFIX + Thread.currentThread().getId();

        // 尝试获取锁
        boolean success = redisUtils.tryLock(KEY_PREFIX + name, threadId, timeout, TimeUnit.SECONDS);

        return Boolean.TRUE.equals(success);
    }

    @Override
    public void unLock() {
        // 获取线程标识(线程id)
        String threadId = ID_PREFIX + Thread.currentThread().getId();

        // 获取锁中的标识
        String id = redisUtils.get(KEY_PREFIX + name);

        // 判断标识是否一致(解决锁误删的问题,比如一个线程(用户1)在执行任务太久,另外一个线程(也是用户1)进来了,
        // 但是redis锁(前一个线程)已经释放了,使得后一个线程获取了锁,前一个锁执行完之后把后一个线程的锁给删除了)
        if (threadId.equals(id)) {
            // 释放锁
            redisUtils.del(KEY_PREFIX + name);
        }

    }
}
