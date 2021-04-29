package com.example.test.demo.vo;

import java.io.Serializable;

/**
 * <p>
 * <code>JwtResponse</code>
 * </p>
 *
 * @description: 要返回给用户的JWT响应
 * @author: lbd
 * @version: 1.0
 * @date: 2021-04-29
 */
public class JwtResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;

    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getToken() {
        return this.jwttoken;
    }
}
