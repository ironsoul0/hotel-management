package com.example.demo.controller;

import com.example.demo.model.Hotel;
import com.example.demo.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private HotelRepository hotelRepository;

    @GetMapping("/")
    public String mainPage(Model model) {
        Iterable<Hotel> hotels = hotelRepository.findAll();
        model.addAttribute("hotels", hotels);
        return "home";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("title", "Signup");
        return "about";
    }
}