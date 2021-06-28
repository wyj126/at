package com.ats.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * DATE: 2021/2/18
 * Author: wyj
 */
@Component
public class RedisUtils {

    /**
     *队列键
     */
    //用于处理审核信息
    public static final String redisAppList = "redisAppList";
    //用于处理告警信息
    public static final String redisWarnList = "redisWarnList";
    //用于处理告警信息
    public static final String redisConcludeList = "redisConcludeList";

    private static final Logger logger = LoggerFactory.getLogger(RedisUtils.class);

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 左存值
     * @param key 键
     * @param value 值
     * @return
     */
    public boolean lpush(String key, Object value) {
        try {
            redisTemplate.opsForList().leftPush(key, value);
            return true;
        } catch (Exception e) {
            logger.error("左存值失败");
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 左取值
     * @param key 键
     * @return
     */
    public boolean lpop(String key) {
        try {
            redisTemplate.opsForList().leftPop(key);
            return true;
        } catch (Exception e) {
            logger.error("左取值失败");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 右存值
     * @param key 键
     * @return
     */
    public Object rpush(String key, Object value) {
        try {
            return redisTemplate.opsForList().rightPush(key, value);
        } catch (Exception e) {
            logger.error("右存值失败");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 右取值
     * @param key 键
     * @return
     */
    public Object rpop(String key) {
        try {
            return redisTemplate.opsForList().rightPop(key);
        } catch (Exception e) {
            logger.error("右取值失败");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查看值
     * @param key 键
     * @param start 开始
     * @param end 结束 0 到 -1代表所有值
     * @return
     */
    public List<Object> lrange(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
