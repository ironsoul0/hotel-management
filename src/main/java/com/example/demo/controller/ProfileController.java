package com.example.demo.controller;

import com.example.demo.model.Reservation;
import com.example.demo.model.Room_type;
import com.example.demo.model.User;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.Room_typeRepository;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private Room_typeRepository room_typeRepository;

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

        List<Reservation> res = new ArrayList<Reservation>();

        for(Reservation r : lst){
            if(r.getUser_id() == user) res.add(r);
        }

        for(Reservation r : res){
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

        reservationrepo.deleteById(id);
        return "redirect:/profile";
    }

    @GetMapping("/profile/edit-book/{id}")
    public String editBooking(@PathVariable Long id, Model model){
        Reservation r = reservationrepo.findById(id).get();
        Iterable<Room_type> lst = r.getRoom_type_id().getHotel_id().getRoom_types();
        model.addAttribute("reservation", r);
        model.addAttribute("roomtypes", lst);
        return "edit-reservation";
    }

    @PostMapping("/profile/edit-book/{id}")
    public String bookRoom(@PathVariable Long id, @RequestParam Long roomtype, @RequestParam String myDate, @RequestParam String myDate2, Model model) throws ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(myDate);
        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(myDate2);
        User u = userRepository.findByUsername(getUsername()).get();
        Room_type rt = room_typeRepository.findById(roomtype).get();
        Reservation r = reservationrepo.findById(id).get();
        r.setCheckinDate(date1);
        r.setCheckoutDate(date2);
        r.setRoom_type_id(rt);
        reservationrepo.save(r);
        return "redirect:/profile";
    }
}
