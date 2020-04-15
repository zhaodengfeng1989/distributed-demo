package com.zhaodf.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 类：RedisManager
 *
 * @author zhaodf
 * @date 2019/10/28
 */
public class RedisManager {
    private static JedisPool jedisPool;
    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(20);
        jedisPoolConfig.setMaxIdle(10);
        jedisPool = new JedisPool(jedisPoolConfig,"192.168.202.128",6379);
    }

    public static Jedis getJedis() throws Exception {
        if (null!=jedisPool){
            return jedisPool.getResource();
        }
        throw new Exception("jedispool is not init ");
    }
}
