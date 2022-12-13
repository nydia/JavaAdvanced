package io.kimmking.cache.Service;

import redis.clients.jedis.Jedis;

/**
 * @Auther: hqlv
 * @Date: 2021/7/18 17:42
 * @Description: Redis 分布式锁
 */
public class RedisLuaLock {

    public static void main(String[] args) {
        lock();
    }

    //加锁
    public static void lock(){
        Jedis  jedis = new Jedis("192.168.99.100", 6379);
        System.out.println(jedis.info());
    }

    //解锁
    public static void unlock(){

    }


}
