package com.example.demo.controller;

import com.example.demo.model.Guest;
import com.example.demo.model.Hotel;
import com.example.demo.model.Reservation;
import com.example.demo.model.Room_type;
import com.example.demo.repository.GuestRepository;
import com.example.demo.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private GuestRepository guestRepository;

    @GetMapping("reservations")
    public List<Reservation> findAllReservations() {
        return (List<Reservation>) reservationRepository.findAll();
    }

//    @GetMapping("/reservations/allGuestReservation")
//    public Set<Room_type> findAllGuestReservation(@RequestParam Long guestId) {
//        Guest guest = guestRepository.findById(guestId).orElseThrow();
//        return guest.getReservations();
//    }
}
