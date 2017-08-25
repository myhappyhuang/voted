package com.demoredis.voted;

import java.util.concurrent.locks.ReentrantLock;
import redis.clients.jedis.Jedis;

/**
 * Created by huangjinlong7 on 2017/6/30.
 */
public class RedisUtill {
    private static Jedis jedis = null;

    private static ReentrantLock lock = new ReentrantLock();//默认非公平锁

    public static Jedis getInstance(){
        if(jedis != null)
            return jedis;
        lock.lock();
        try {
            if(jedis == null){
                jedis = new Jedis("10.33.42.51");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
        return jedis;
    }
}
