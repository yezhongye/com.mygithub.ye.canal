package com.ye.redis.cllient.test;

import com.ye.redis.client.pubsub.util.RedisMsgPubSubListener;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * Created by zjx on 2017/12/20.
 */
public class SubscribeTest {
    @Test
    public void testSubscribe() throws Exception{
        Jedis jedis = new Jedis("localhost");
        RedisMsgPubSubListener listener = new RedisMsgPubSubListener();
        jedis.subscribe(listener, "redisChatTest");
        //other code
    }

    @Test
    public void testPublish() throws Exception{
        Jedis jedis = new Jedis("localhost");
        jedis.publish("redisChatTest", "我是天才");
        Thread.sleep(5000);
        jedis.publish("redisChatTest", "我要发版了");
        Thread.sleep(5000);
        jedis.publish("redisChatTest", "发版陈公公");
    }
}
