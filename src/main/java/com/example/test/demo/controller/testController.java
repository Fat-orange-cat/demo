package com.example.test.demo.controller;

import com.example.test.demo.bean.Users;
import com.example.test.demo.repository.UsersRepository;
import com.example.test.demo.utils.Jedisutil;
import com.example.test.demo.utils.SerializeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.List;

@RestController
@RequestMapping("test")
public class testController {

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/hello")
    public String hello(){
        String key = "redisKeyTest";
        List<Users> list = usersRepository.findAll();;
        Jedis jedis = Jedisutil.getJedis();
        try {
            SerializeUtil su = new SerializeUtil();
            jedis.set(key.getBytes(), su.serialize(list));
        } finally {
            Jedisutil.returnResource(jedis);
        }
        return "hello";
    }
    @GetMapping("/error")
    public String error(){
        return "login error";
    }

}
