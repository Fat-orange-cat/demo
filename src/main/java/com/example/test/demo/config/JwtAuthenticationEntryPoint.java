package com.example.test.demo.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * <p>
 * <code>JwtAuthenticationEntryPoint</code>
 * </p>
 *
 * @description: 拒绝每个未经身份验证的请求并转发 //并发送错误代码401
 * @author: lbd
 * @version: 1.0
 * @date: 2021-04-29
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.sendRedirect("login.html");//.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
