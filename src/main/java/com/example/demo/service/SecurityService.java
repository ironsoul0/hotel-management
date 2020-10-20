package com.example.demo.service;

public interface SecurityService {
    String findLoggedInUserName();

    void autoLogin (String userName, String password);
}
