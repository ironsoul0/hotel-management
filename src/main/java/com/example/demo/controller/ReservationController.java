package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.model.Hotel;
import com.example.demo.model.Reservation;
import com.example.demo.model.Room_type;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageTranscoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("reservations")
    public List<Reservation> findAllReservations() {
        return (List<Reservation>) reservationRepository.findAll();
    }

//    @GetMapping("/reservations/{userId}")
//    public List<Reservation> findAllGuestReservation(@RequestParam Long userId) {
//        Iterable<Reservation> allReservation = reservationRepository.findAll();
//        ArrayList<Reservation> userReservation = new ArrayList<>();
//        for (Reservation reservation : allReservation) {
//            if (reservation.getUser_id().getId() == userId) {
//                userReservation.add(reservation);
//            }
//        }
//        return (List<Reservation>) userReservation;
//    }
}
