package com.example.demo.controller;

import com.example.demo.model.Hotel;
import com.example.demo.model.Reservation;
import com.example.demo.model.Room_type;
import com.example.demo.model.User;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.Room_typeRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/hotels")
public class HotelRestController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Room_typeRepository room_typeRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    private String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

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

    private long calculatePrice(Date d1, Date d2, int bp){
        long diffInMils = Math.abs(d2.getTime() - d1.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMils, TimeUnit.MILLISECONDS);
        return diff * bp;
    }

    @PostMapping("/book-room")
    public String bookRoomReal(@RequestParam Long roomtype, @RequestParam String myDate, @RequestParam String myDate2, Model model) throws ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(myDate);
        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(myDate2);
        User u = userRepository.findByUsername(getUsername()).get();
        Room_type rt = room_typeRepository.findById(roomtype).get();
        long price = calculatePrice(date1, date2, rt.getBase_price());
        Reservation r = new Reservation(date1, date2, (int) price, 1, u, false);
        r.setRoom_type_id(rt);
        reservationRepository.save(r);
        return "Success";
    }

    @PostMapping("/book-room-price")
    public Long bookRoom(@RequestParam Long roomtype, @RequestParam String myDate, @RequestParam String myDate2, Model model) throws ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(myDate);
        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(myDate2);
        Room_type rt = room_typeRepository.findById(roomtype).get();
        long price = calculatePrice(date1, date2, rt.getBase_price());
        return price;
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
