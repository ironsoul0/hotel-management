package com.example.demo.controller;

import com.example.demo.model.Reservation;
import com.example.demo.model.User;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.UserRepository;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.controller.UserAuthenticatorController;

import java.security.Principal;
import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationrepo;

    private String getUsername(){
        Principal userDetails = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getName();
    }

    private User getUser() {
        String username = getUsername();
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!: " + getUsername());
        User user = userRepository.findByUsername(username).get();
        return user;
    }

    @GetMapping("/profile")
    public String showProfile(Model model){
        User user = getUser();
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!: " + user.getUsername());
        model.addAttribute("user", user);
        Iterable<Reservation> lst = reservationrepo.findAll();
        model.addAttribute("reservations", lst);
        return "profile";
    }
}
