package com.example.demo.controller;

import com.example.demo.model.Hotel;
import com.example.demo.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/hotels")
public class HotelRestController {

    @Autowired
    private HotelRepository hotelRepository;

    @GetMapping("/{city}")
    public Iterable<Hotel> hotelSearch(@PathVariable String city, Model model) {
        Iterable<Hotel> hotelsAll = hotelRepository.findAll();
        ArrayList<Hotel> resHotels = new ArrayList<>();
        for (Hotel hotel : hotelsAll) {
            if (hotel.getAddress().toLowerCase().contains(city.toLowerCase())) {
                resHotels.add(hotel);
            }
        }
        return resHotels;
    }

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
