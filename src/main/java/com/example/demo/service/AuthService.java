package com.example.demo.service;
import com.example.demo.repository.UserInfoRepository;
import com.example.demo.model.UserInfo;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {


    public final UserInfoRepository repository;
    public  final PasswordEncoder passwordEncoder;



    public AuthService(UserInfoRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public String addUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "user added to system ";
    }
}