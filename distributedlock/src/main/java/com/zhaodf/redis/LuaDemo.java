package com.zhaodf.redis;

import redis.clients.jedis.Jedis;

/**
 * 类：LuaDemo
 *
 * @author zhaodf
 * @date 2019/11/2
 */
public class LuaDemo {
    public static void main(String[] args) {
        try {
            Jedis jedis = RedisManager.getJedis();
            String luaScript = "return redis.call('get','hello')";
            String sha = jedis.scriptLoad(luaScript);
            System.out.println("脚本生成的摘要为："+sha);
            String value = (String)jedis.evalsha(sha);
            System.out.println("根据脚本摘要执行的返回的value为："+value);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
