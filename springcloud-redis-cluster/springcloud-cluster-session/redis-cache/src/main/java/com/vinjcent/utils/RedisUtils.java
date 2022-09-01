package com.vinjcent.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("all")
@Component
public final class RedisUtils {

    @Autowired
    private RedisTemplate redisTemplate;

    //=============================common===================================

    /**
     * 指定缓存失效时间
     * @param key   键
     * @param time  时间(秒)
     * @return
     */
    public boolean expire(String key, long time){
        try {
            if(time > 0){
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key获取过期时间
     * @param key   键(不能为null)
     * @return      时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key){ return redisTemplate.getExpire(key,TimeUnit.SECONDS);}

    /**
     *判断key是否存在
     * @param key   键
     * @return      true存在/false不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     * @param key   键,可以传递一个值或多个
     */
    public void del(String... key) {
        if(key != null && key.length > 0){
            if (key.length == 1){
                redisTemplate.delete(key[0]);
            }else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }


    //=============================String===================================

    /**
     * 普通缓存获取
     * @param key   key键
     * @return      根据键获取对应的值
     */
    public Object get(String key){ return key == null ? null : redisTemplate.opsForValue().get(key);}

    /**
     * 普通缓存放入
     * @param key   键
     * @param value 值
     * @return      true存放成功/false存放失败
     */
    public boolean set(String key, Object value){
        try {
            redisTemplate.opsForValue().set(key,value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *普通缓存放入并设置时间
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于0 将设置无期限
     * @return      true存放成功/false存放失败
     */
    public boolean set(String key, Object value, long time){
        try {
            if(time > 0){
                redisTemplate.opsForValue().set(key,value,time,TimeUnit.SECONDS);
            }else {
                set(key,value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 递增
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return      返回递增后的值
     */
    public long incr(String key, long delta){
        if(delta < 0){
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     *递减
     * @param key   键
     * @param delta 要增加几(小于0)
     * @return      返回递减后的值
     */
    public long decr(String key, long delta){
        if(delta < 0){
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    //=============================Map===================================

    /**
     * 根据hashKey获取hash列表有多少元素
     * @param key   键(hashKey)
     * @return      返回列表大小
     */
    public long hsize(String key) {
        try {
            return redisTemplate.opsForHash().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0l;
        }
    }

    /**
     * HashGet  根据"项 中的 键 获取"
     * @param key   键(hashKey)   不能为null
     * @param item  项   不能为null
     * @return
     */
    public Object hget(String key, String item){ return redisTemplate.opsForHash().get(key,item);}

    /**
     * 获取HashKey对应的所有键值
     * @param key   键(hashKey)
     * @return      对应的多个键值
     */
    public Map<Object, Object> hmget(String key) { return redisTemplate.opsForHash().entries(key);}


    /**
     * HashSet  存入多个键值对
     * @param key   键(hashKey)
     * @param map   map 对应多个键值对
     * @return
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key,map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * HashSet存入并设置时间
     * @param key   键(hashKey)
     * @param map   对应多个键值
     * @param time  时间(秒)
     * @return      true存入成功/false存入失败
     */
    public boolean hmset(String key, Map<String, Object> map, long time){
        try {
            redisTemplate.opsForHash().putAll(key,map);
            if (time > 0){
                expire(key,time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key   键(hashKey)
     * @param item  项
     * @param value 值
     * @return      true存入成功/false存入失败
     */
    public boolean hset(String key, String item, Object value){
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建,并设置有效时间
     * @param key   键(hashKey)
     * @param item  项
     * @param value 值
     * @param time  时间(秒)   注意: 如果已经在hash表有时间,这里将会替换所有的时间
     * @return      true存入成功/false存入失败
     */
    public boolean hset(String key, String item, Object value, long time){
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0){
                expire(key,time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除hash表中的值
     * @param key   键(hashKey)   不能为null
     * @param item  项   可以是多个   不能为null
     */
    public void hdel(String key, Object... item){
        redisTemplate.opsForHash().delete(key,item);
    }

    /**
     * 判断hash表是否有该项的值
     * @param key   键(hashKey)   不能为null
     * @param item  项   不能为null
     * @return      true存在/false不存在
     */
    public boolean hHasKey(String key, String item){
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增   如果不存在,就会创建一个,并把新增后的值返回
     * @param key   键(hashKey)
     * @param item  项
     * @param by    要增加几(大于0)
     * @return
     */
    public double hincr(String key, String item, double by){
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     * @param key   键(hashKey)
     * @param item  项
     * @param by    要减少几(小于0)
     * @return
     */
    public double hdecr(String key, String item, double by){
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    //=============================Set===================================

    /**
     * 根据key获取Set中的所有值
     * @param key   键
     * @return      返回key的所有值
     */
    public Set<Object> sGet(String key){
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据value从一个Set集合中查询一个值,是否存在
     * @param key   键
     * @param value 值
     * @return      true存在/false不存在
     */
    public boolean sHasKey(String key, Object value){
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     * @param key       键
     * @param values    值
     * @return          返回成功个数
     */
    public long sSet(String key, Object... values){
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将set数据放入缓存,并设置有效时间
     * @param key       键
     * @param time      时间(秒)
     * @param values    值,可以是多个
     * @return          返回成功个数
     */
    public long sSetAndTime(String key, long time, Object... values){
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if(time > 0){
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     * @param key   键
     * @return
     */
    public long sGetSetSize(String key){
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除值为values的
     * @param key       键
     * @param values    值(可以是多个)
     * @return          返回移除的个数
     */
    public long setRemove(String key, Object... values){
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    //=============================List===================================

    /**
     * 获取list缓存的长度
     * @param key   键
     * @return      返回list的长度
     */
    public long lGetListSize(String key){
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     * @param key   键
     * @param index 索引 index >= 0 时, 0:表头, 1:第二个元素,以此类推...    index < 0 时, -1:表尾, -2:倒数第二个元素,以此类推
     * @return
     */
    public Object lgetIndex(String key, long index){
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将list放入缓存
     * @param key   键
     * @param value 值
     * @return      true存放成功/false存放失败
     */
    public boolean lSet(String key, Object value){
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return      true存放成功/false存放失败
     */
    public boolean lSet(String key, Object value, long time){
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0){
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list集合放入缓存
     * @param key       键
     * @param values    值
     * @return          true存放成功/false存放失败
     */
    public boolean lSet(String key, List<Object> values){
        try {
            redisTemplate.opsForList().rightPushAll(key, values);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list集合放入缓存,并设置有效时间
     * @param key       键
     * @param values    值
     * @param time      时间(秒)
     * @return          true存放成功/false存放失败
     */
    public boolean lSet(String key, List<Object> values, long time){
        try {
            redisTemplate.opsForList().rightPushAll(key, values);
            if (time > 0){
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     * @param key   键
     * @param value 值
     * @param index 索引
     * @return
     */
    public boolean lUpdateIndex(String key, Object value, long index){
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除N个值为value
     * @param key       键
     * @param value     值
     * @param number    移除多少个
     * @return          返回移除的个数
     */
    public long lRemove(String key, Object value, long number){
        try {
            Long count = redisTemplate.opsForList().remove(key, number, value);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
