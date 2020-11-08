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
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationrepo;

    private String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    private User getUser() {
        String username = getUsername();
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!: " + getUsername());
        User user = userRepository.findByUsername(username).get();
        return user;
    }

    @GetMapping("/profile")
    public String showProfile(Model model){
        ArrayList<Reservation> upcoming = new ArrayList<Reservation>();
        ArrayList<Reservation> current = new ArrayList<Reservation>();
        ArrayList<Reservation> past = new ArrayList<Reservation>();

        Instant now = LocalDateTime.now().toInstant(ZoneOffset.ofHours(6));

        User user = getUser();
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!: " + user.getUsername());
        model.addAttribute("user", user);
        Iterable<Reservation> lst = reservationrepo.findAll();

        for(Reservation r : lst){
            Instant a = r.getCheckinDate().toInstant();
            Instant b = r.getCheckoutDate().toInstant();

            if(a.compareTo(now) > 0){
                upcoming.add(r);
            } else if(b.compareTo(now) < 0){
                past.add(r);
            } else {
                current.add(r);
            }
        }

        model.addAttribute("current_reservations", current);
        model.addAttribute("past_reservations", past);
        model.addAttribute("upcoming_reservations", upcoming);
        return "profile";
    }

    @GetMapping("/profile/delete-book/{id}")
    public String deleteBooking(@PathVariable Long id){
        if(!reservationrepo.existsById(id)){
            return "redirect:/profile";
        }

        Reservation x = reservationrepo.findById(id).get();

        Instant checkin = x.getCheckinDate().toInstant();
        Instant checkout = x.getCheckoutDate().toInstant();

        Instant now = LocalDateTime.now().toInstant(ZoneOffset.ofHours(6));

        if(now.compareTo(checkin) < 0 && now.compareTo(checkout) < 0){
            reservationrepo.deleteById(id);
        }

        return "redirect:/profile";
    }
}
