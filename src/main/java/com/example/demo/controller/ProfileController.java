package com.example.demo.controller;

import com.example.demo.model.Hotel;
import com.example.demo.model.Reservation;
import com.example.demo.model.Room_type;
import com.example.demo.model.User;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.Room_typeRepository;
import com.example.demo.repository.UserRepository;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.controller.UserAuthenticatorController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.TimeUnit;

// @Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/profile")
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

    @GetMapping("/reservations")
    public HashMap<String, Object> showProfile(Model model){
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

        HashMap<String, Object> map = new HashMap<>();
        map.put("upcoming", upcoming);
        map.put("past", past);
        map.put("current", current);

        return map;
    }

    @GetMapping("/delete-book/{id}")
    public String deleteBooking(@PathVariable Long id){
        if(!reservationrepo.existsById(id)){
            return "Fail";
        }

        reservationrepo.deleteById(id);
        return "Success";
    }

    @GetMapping("/get-reservation/{id}")
    public Reservation editBooking(@PathVariable Long id){
        Reservation r = reservationrepo.findById(id).get();
        return r;
    }

    @JsonIgnore
    @GetMapping("/get-hotel/{id}")
    public Hotel getBooking(@PathVariable Long id){
        Reservation r = reservationrepo.findById(id).orElseThrow();
        return r.getRoom_type_id().getHotel_id();
    }

    private long calculatePrice(Date d1, Date d2, int bp){
        long diffInMils = Math.abs(d2.getTime() - d1.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMils, TimeUnit.MILLISECONDS);
        return diff * bp;
    }

    @PostMapping("/edit-book/{id}")
    public String bookRoom(@PathVariable Long id, @RequestParam Long roomtype, @RequestParam String myDate, @RequestParam String myDate2) throws ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(myDate);
        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(myDate2);
        User u = userRepository.findByUsername(getUsername()).get();
        Room_type rt = room_typeRepository.findById(roomtype).get();
        Reservation r = reservationrepo.findById(id).get();
        r.setCheckinDate(date1);
        r.setCheckoutDate(date2);
        r.setRoom_type_id(rt);
        r.setApproved(false);

        int price = (int) calculatePrice(date1, date2, rt.getBase_price());
        r.setPrepaid_price(price);
        reservationrepo.save(r);
        return "Success";
    }
}
