package com.example.test.demo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * <code>JwtRequest</code>
 * </p>
 *
 * @description: 存储接收到的用户名和密码
 * @author: lbd
 * @version: 1.0
 * @date: 2021-04-29
 */
@Data
public class JwtRequest implements Serializable {

    private static final long serivalVersionUID = 5926468583005150707L;
    private String username;
    private String password;

    public JwtRequest(){}

    public JwtRequest(String username,String password){
        this.setUsername(username);
        this.setPassword(password);
    }
}
