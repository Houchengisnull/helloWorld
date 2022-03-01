package org.hc.learning.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 往Redis中发布消息
 */
public class Pub {

    public static void main(String[] args) {
        // 连接redis
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
        Jedis resource = pool.getResource();
        System.out.println("connect to redis");
        // 发布
        Date date = new Date();
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        String message = dateStr + ":\thello world";
        resource.publish(RedisConstant.channel, message);
        System.out.println("publish \"" + message + "\" to " + RedisConstant.channel);
        pool.close();
    }

}
