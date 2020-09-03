package com.youzi.common.server.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@EnableCaching
public class RedisBaseDao {

    /**
     * redisTemplate
     */
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 删除缓存<br>
     * 根据key精确匹配删除
     *
     * @param key key
     */
    @SuppressWarnings("unchecked")
    public void deleteKey(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 取得缓存（字符串类型）
     *
     * @param key key
     * @return String
     */
    public String getString(String key) {
        final String value = (String) redisTemplate.boundValueOps(key).get();
        return value;
    }

    /**
     * 将value对象写入缓存
     *
     * @param key key
     * @param value value
     */
    public void setString(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 将value对象写入缓存
     *
     * @param key key
     * @param value value
     *            失效时间(秒)
     */
    public void set(String key, Object value) {
        if (value.getClass().equals(String.class)) {
            redisTemplate.opsForValue().set(key, value.toString());
        }

    }

    /**
     * 指定缓存的失效时间
     *
     * @author FangJun
     * @date 2016年8月14日
     * @param key key
     *            缓存KEY
     * @param timeout timeout
     *            失效时间(秒)
     */
    public void expire(String key, long timeout) {
        if (timeout > 0) {
            redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        }
    }

    /**
     * 获取过期时间
     * @param key
     * @return
     */
    public Long getExpire(String key) {
        return redisTemplate.boundValueOps(key).getExpire();
    }

    /**
     * 模糊查询keys
     *
     * @param pattern pattern
     * @return set
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 计数器
     *
     * @param key key
     */
    public void increment(String key) {
        redisTemplate.opsForValue().increment(key, 1);
    }

    /**
     * 可设置步长计数器
     * @param key
     * @param step
     */
    public Long increment(String key, Long step) {
        return redisTemplate.opsForValue().increment(key, step);
    }

    /**
     * 首次设置返回true
     * @param key
     * @param value
     * @return
     */
    public Boolean setIfAbsent(String key, Object value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 批量设置hash键值对
     * @param key
     * @param dataMap
     */
    public void putAll(String key, Map<Object, Object> dataMap) {
        redisTemplate.boundHashOps(key).putAll(dataMap);
    }

    /**
     * 设置hash键值对
     * @param key
     * @param hkey
     * @param hval
     */
    public void put(String key, Object hkey, Object hval) {
        redisTemplate.boundHashOps(key).put(hkey, hval);
    }

    /**
     * 获取单个hash键值对的值
     * @param key
     * @param hkey
     * @return
     */
    public Object get(String key, Object hkey) {
        return redisTemplate.boundHashOps(key).get(hkey);
    }

    /**
     * 批量获取hash键值对得值
     * @param key
     * @param keys
     * @return
     */
    public List<Object> multiGet(String key, List<String> keys) {
        return redisTemplate.boundHashOps(key).multiGet(Collections.singleton(keys));
    }

    /**
     * 获取hash单个键值对
     * @param key
     * @return
     */
    public Map<Object, Object> entries(String key) {
        return redisTemplate.boundHashOps(key).entries();
    }

    /**
     * 指定缓存的失效时间和单位
     *
     * @author FangJun
     * @date 2016年8月14日
     * @param key key
     *            缓存KEY
     * @param timeout timeout
     *            失效时间(秒)
     */
    public void expireAndTimeUnit(String key, long timeout, TimeUnit timeUnit) {
        if (timeout > 0) {
            redisTemplate.expire(key, timeout, timeUnit);
        }
    }

    /**
     * 获取一个redis锁
     *
     * @param lockKey        锁住的key
     * @param lockExpireMils 锁住的时长。如果超时未解锁，视为加锁线程死亡，其他线程可夺取锁
     * @return
     */
    public boolean lock(String lockKey, long lockExpireMils) {
        return (Boolean) redisTemplate.execute((RedisCallback) connection -> {
            long nowTime = System.currentTimeMillis();
            Boolean acquire = connection.setNX(lockKey.getBytes(), String.valueOf(nowTime + lockExpireMils + 1).getBytes());
            if (acquire) {
                return Boolean.TRUE;
            } else {
                byte[] value = connection.get(lockKey.getBytes());
                if (Objects.nonNull(value) && value.length > 0) {
                    long oldTime = Long.parseLong(new String(value));
                    if (oldTime < nowTime) {
                        //connection.getSet：返回这个key的旧值并设置新值。
                        byte[] oldValue = connection.getSet(lockKey.getBytes(), String.valueOf(nowTime + lockExpireMils + 1).getBytes());
                        //当key不存时会返回空，表示key不存在或者已在管道中使用
                        return oldValue == null ? false : Long.parseLong(new String(oldValue)) < nowTime;
                    }
                }
            }
            return Boolean.FALSE;
        });
    }

    /**
     * 删除锁
     */
    public void unlock(String key){
        redisTemplate.delete(key);
    }



    /**
     * 获取缓存<br>
     * 注：基本数据类型(Character除外)，请直接使用get(String key, Class<T> clazz)取值
     *
     * @param key
     * @return
     */
    public Object getObj(String key) {
        return redisTemplate.boundValueOps(key).get();
    }

    public void setObj(String key,Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

}