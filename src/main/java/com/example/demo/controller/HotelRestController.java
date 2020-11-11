package com.example.demo.controller;

import com.example.demo.model.Hotel;
import com.example.demo.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/hotels")
public class HotelRestController {

    @Autowired
    private HotelRepository hotelRepository;

    @GetMapping("/")
    public Iterable<Hotel> mainPage(Model model) {
        Iterable<Hotel> hotels = hotelRepository.findAll();
        return hotels;
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("title", "Signup");
        return "about";
    }
}
