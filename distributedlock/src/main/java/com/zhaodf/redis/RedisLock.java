package com.zhaodf.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 类：RedisLock
 *
 * @author zhaodf
 * @date 2019/10/28
 */
public class RedisLock {
    public String getLock(String key,int timeout){
        try {
            Jedis jedis = RedisManager.getJedis();
            String value = UUID.randomUUID().toString();
            long end  = System.currentTimeMillis()+timeout;
            while (System.currentTimeMillis()<end){
                if(jedis.setnx(key,value)==1){
                    //获取锁成功，设置key失效时间
                    jedis.expire(key,timeout);
                    return value;
                }
                if (jedis.ttl(key)==-2){
                    jedis.expire(key, timeout);
                }
                TimeUnit.SECONDS.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean releaseLock(String key,String value){
        try {
            Jedis jedis = RedisManager.getJedis();
            while (true){
                jedis.watch(key);
                //判断当前获得锁的进程的锁和当前锁是否是同一个锁
                if (value.equals(jedis.get(key))){
                    Transaction transaction = jedis.multi();
                    transaction.del(key);
                    List<Object> list = transaction.exec();
                    if(null==list){
                        continue;
                    }
                    return true;
                }
                jedis.unwatch();
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        RedisLock redisLock = new RedisLock();
        String lockId = redisLock.getLock("lock:zhaodf",10);
        if(null!=lockId){
            System.out.println("获得锁成功");
        }
        redisLock.releaseLock("lock:zhaodf",lockId);
        String lockId2 = redisLock.getLock("lock:zhaodf",10);
        System.out.println("再次获取锁："+lockId2);
    }
}
