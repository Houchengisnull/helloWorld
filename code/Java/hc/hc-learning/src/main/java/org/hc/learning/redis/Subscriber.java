package org.hc.learning.redis;

import redis.clients.jedis.JedisPubSub;

public class Subscriber extends JedisPubSub {

    /**
     * 收到消息时回调
     * @param channel
     * @param message
     */
    @Override
    public void onMessage(String channel, String message) {
        super.onMessage(channel, message);
        System.out.println("receive redis channel[" + channel + "]'s message:" + message);
    }

    /**
     * 订阅消息时回调
     * @param channel
     * @param subscribedChannels
     */
    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        super.onSubscribe(channel, subscribedChannels);
        System.out.println("subscribe redis channel[" + channel + "]");
    }

    /**
     * 取消订阅时回调
     * @param channel
     * @param subscribedChannels
     */
    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        super.onUnsubscribe(channel, subscribedChannels);
        System.out.println("unsubscribe redis channel[" + channel + "]");
    }
}
