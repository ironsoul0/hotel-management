package com.example.demo.service;

import com.example.demo.model.Guest;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;


@Service
public class GuestServiceImpl implements GuestService {
    @Autowired
    private GuestRepository guestRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(Guest guest) {
        guest.setPassword(bCryptPasswordEncoder.encode(guest.getPassword()));
        guest.setRoles(new HashSet< >(roleRepository.findAll()));
        guestRepository.save(guest);
    }

    @Override
    public Guest findByUsername(String username) {
        return guestRepository.findByUsername(username);
    }
}
