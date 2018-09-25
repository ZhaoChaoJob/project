package com.geotmt.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis cache 工具类
 * <p>
 * Created by cheng on 16/6/26.
 */
@Service
public  class RedisService {

    private static Logger logger = LoggerFactory.getLogger(RedisService.class);

    private final RedisTemplate redisTemplate;

    @Autowired
    public RedisService(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }
    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }
    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }
    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }
    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }
    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 自增
     * @param key
     * @param value
     * @return
     */
    public long increment(final String key, Long value) {
        long keyid = 0l;
        try {
            ValueOperations<String, Object> valueOper= redisTemplate.opsForValue();
            keyid = valueOper.increment(key,value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyid;
    }

    /**
     * 获取List
     * @param key 键值
     * @param start 起始值
     * @param end 结束位置-- -1表示全部
     * @return
     */
    public List<String> lrange(String key, long start, long end){
        List<String> list = new ArrayList<>() ;
        try{
            ListOperations<String, String> valueOper = redisTemplate.opsForList() ;
            list = valueOper.range(key, start, end) ;
        }catch (Exception e){
            e.printStackTrace();
        }
        return list ;
    }

    /**
     * 出队
     * @param key 键值
     * @param count 个数
     * @param value 值
     * @return 个数
     */
    public Long lrem(String key, long count, String value) {
        Long num = 0L ;
        try{
            ListOperations<String, String> valueOper = redisTemplate.opsForList() ;
            num = valueOper.remove(key,count,value) ;
        }catch (Exception e){
            e.printStackTrace();
        }
        return num ;
    }

    /**
     * 右入队
     * @param key 键值
     * @param strings strings
     * @return 个数
     */
    public Long listRpush(String key, String... strings) {
        Long num = 0L ;
        try{
            ListOperations<String, String> valueOper = redisTemplate.opsForList() ;
            num = valueOper.rightPushAll(key,strings) ;
        }catch (Exception e){
            e.printStackTrace();
        }
        return num ;
    }

    /**
     * 获取list的长度
     * @param key 键值
     * @return 长度
     */
    public Long listSize(String key) {
        Long num = 0L ;
        try{
            ListOperations<String, String> valueOper = redisTemplate.opsForList() ;
            num = valueOper.size(key) ;
        }catch (Exception e){
            e.printStackTrace() ;
        }
        return num ;
    }

    /**
     * 消减队列
     * @param key 键值
     * @param start 起始值
     * @param end 终点值
     */
    public void listTrim(String key, long start, long end) {
        try{
            ListOperations<String, String> valueOper = redisTemplate.opsForList() ;
            valueOper.trim(key, start, end);
        }catch (Exception e){
            e.printStackTrace() ;
        }
    }

    /**
     * redis发布消息
     *
     * @param channel 渠道
     * @param message 消息
     */
    public void sendMessage(String channel, String message) {
        redisTemplate.convertAndSend(channel, message);
    }

}

