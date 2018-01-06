package com.ye.canal.client.redis;

import com.ye.canal.client.util.RedisUtils;
import org.junit.Test;

/**
 * Created by zjx on 2017/12/14 0014.
 */
public class RedisTest {
    @Test
    public void setRedisData(){
       String res =  RedisUtils.stringSet("Too","123");
        System.out.println("结果："+res);
    }

    @Test
    public void getRedisData(){
        String res = RedisUtils.stringGet("Too");
        System.out.println("结果："+res);
    }
}
