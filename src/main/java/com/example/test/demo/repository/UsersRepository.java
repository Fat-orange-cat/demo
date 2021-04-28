package com.example.test.demo.repository;

import com.example.test.demo.bean.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;


public interface UsersRepository extends JpaRepository<Users, BigInteger> {
    Users findByName(String name);
}
