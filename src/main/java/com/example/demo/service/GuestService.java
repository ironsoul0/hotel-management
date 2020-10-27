package com.example.demo.service;

import com.example.demo.model.Guest;

public interface GuestService {
    void save(Guest guest);

    Guest findByUsername(String username);
}