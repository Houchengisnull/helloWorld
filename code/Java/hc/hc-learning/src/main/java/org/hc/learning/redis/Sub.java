package org.hc.learning.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 订阅通道中的消息
 */
public class Sub {

    public static void main(String[] args) {
        Subscriber subscriber = new Subscriber();
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
        Jedis resource = pool.getResource();
        resource.subscribe(subscriber, RedisConstant.channel);
    }
}
