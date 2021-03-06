package com.example.test.demo.controller;

import com.example.test.demo.service.MyUserDetailService;
import com.example.test.demo.utils.JwtTokenUtil;
import com.example.test.demo.vo.JwtRequest;
import com.example.test.demo.vo.JwtResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * <p>
 * <code>JwtAuthenticationController</code>
 * </p>
 *
 * @description: 暴露/authenticate
 * @author: lbd
 * @version: 1.0
 * @date: 2021-04-29
 */
@RestController
@CrossOrigin
public class JwtAuthenticationController {

    private final AuthenticationManager  authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final MyUserDetailService jwtInMemoryUserDetailsService;

    public JwtAuthenticationController(AuthenticationManager  authenticationManager,
                                       JwtTokenUtil jwtTokenUtil,
                                       MyUserDetailService jwtInMemoryUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtInMemoryUserDetailsService = jwtInMemoryUserDetailsService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(),
                authenticationRequest.getPassword());
        final UserDetails userDetails = jwtInMemoryUserDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));

    }

    private void authenticate(String name,String password) throws Exception {
        Objects.requireNonNull(name);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(name,password));

        }catch (DisabledException e){
            throw new Exception("USER_DISABLED" ,e);
        }catch (BadCredentialsException e){
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
