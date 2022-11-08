package com.vinjcent.cache;

import com.vinjcent.utils.ApplicationContextUtils;
import com.vinjcent.utils.RedisUtils;
import org.apache.ibatis.cache.Cache;
import org.springframework.util.DigestUtils;

// 自定义Redis缓存实现
public class RedisCache implements Cache {

    // 当前放入缓存的mapper的namespace
    private final String id;

    // 必须有一个构造方法,并且带有一个String类型的参数id
    public RedisCache(String id) {
        this.id = id;
    }

    // 返回cache唯一标识
    @Override
    public String getId() {
        return this.id;
    }

    // 缓存中放入值
    @Override
    public void putObject(Object key, Object value) {

        // System.out.println("key: " + key);
        // System.out.println("value: " + value);

        // 1.通过application工具类获取redisTemplate
        RedisUtils redisUtils = (RedisUtils) ApplicationContextUtils.getBean("redisUtils");

        // 2.使用redis中的hash类型作为缓存存储模型 key hashKey value
        redisUtils.hset(id, getKeyToMD5(key.toString()), value);

    }

    // 缓存中获取值
    @Override
    public Object getObject(Object key) {

        // 1.通过application工具类获取redisTemplate
        RedisUtils redisUtils = (RedisUtils) ApplicationContextUtils.getBean("redisUtils");

        // 2.根据key,从redis的hash类型中获取数据
        return redisUtils.hget(id, getKeyToMD5(key.toString()));

    }

    // 根据指定的key删除缓存,注意这个方法为mybatis的保留方法,默认没有实现
    @Override
    public Object removeObject(Object o) {
        return null;
    }

    // 清空缓存,增、删、改都会清空缓存
    @Override
    public void clear() {

        // 1.通过application工具类获取redisTemplate
        RedisUtils redisUtils = (RedisUtils) ApplicationContextUtils.getBean("redisUtils");

        // 2.清空namespace
        redisUtils.del(id);     // 清空该类型的所有key
    }

    // 用来计算缓存的数量
    @Override
    public int getSize() {

        // 1.通过application工具类获取redisTemplate
        RedisUtils redisUtils = (RedisUtils) ApplicationContextUtils.getBean("redisUtils");

        // 2.获取hash中key-value中的数量
        return (int) redisUtils.hsize(id);
    }

    // 封装一个对key进行md5处理方法
    // 原理: 为了保证不同文件产生的键值不一致,并且缩短key的存储长度,使用md5能够产生相同文件唯一的32位的16进制的加密密文
    public String getKeyToMD5(String key) {
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }
}
