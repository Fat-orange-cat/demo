package com.example.test.demo.utils;

import com.example.test.demo.config.JedisConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
public class Jedisutil {

    private static JedisConfig jedisConfig;

    public Jedisutil(JedisConfig jedisConfig){
        this.jedisConfig = jedisConfig;
    }

    public static JedisPool getPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setTestOnBorrow(false);
        config.setTestWhileIdle(true);
        config.setMaxTotal(1000);
        config.setMaxIdle(10);
        config.setMaxWaitMillis(10000L);
        config.setMinIdle(5);
        config.setTimeBetweenEvictionRunsMillis(100L);

        return new JedisPool(config,jedisConfig.getHost(),jedisConfig.getPort());
    }

    public static Jedis getJedis(){
        Jedis jedis = getPool().getResource();
        if(StringUtils.isNotEmpty(jedisConfig.getPassword())){
            jedis.auth(jedisConfig.getPassword());
        }
        return jedis;
    }

    public static void returnResource(Jedis redis) {
        if (redis != null){
            redis.close();
        }
    }
}
