package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/desk")
public class DeskClerkController {

    @Qualifier("userRepository")
    @Autowired
    private UserRepository guestRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private HotelRepository hotelRepository;

    // I'm not sure if we have to request hotelId, but for now I think it is okay
    // also could be a function for button, but before pressing button, desk clerk has to enter the hotelId
    // for now it is okay
    // need to separate User and Guest and Employees and change ER model
    @GetMapping("/allReservations") // this one works
    public Set<Reservation> showAllReservations (@RequestParam Long hotelId) {

        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow();
        List<Reservation> reservations = reservationRepository.findAll();

        Set <Room_type> hotelRoomTypes = hotel.getRoom_types();
        Set <Reservation> reservationSet = new HashSet<>();

        for (Reservation r : reservations) {
            for (Room_type t : hotelRoomTypes) {
                if (t.getRoom_type_id().equals(r.getRoom_type_id().getRoom_type_id())) {
                    reservationSet.add(r);
                    break; // I assume that it breaks second for statement
                }
            }
        }

        return reservationSet;
    }

    // this is implemented as a function for button
    // it returns roomId that was set for user
    @PostMapping("/approveReservation") // this one works
    public Long approveReservation (@RequestParam Long reservationId, @RequestParam Long hotelId) {

        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow();

        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow();
        Set <Room> rooms = hotel.getRooms();

        for (Room r : rooms) {

            if
                    (r.getRoom_type().getRoom_type_id()
                    .equals(reservation.getRoom_type_id().getRoom_type_id())
                    && !r.getOccupied()
            ) {

                r.setOccupied(true);

                return r.getId();
            }
        }

        hotelRepository.save(hotel);
        reservationRepository.delete(reservation);


        return (long) -1; // specific exception is needed, but for now it is negative value
    }

    // this is implemented as a function for button
    @DeleteMapping("/removeReservation") // works
    public void removeReservation (@RequestParam Long reservationId, @RequestParam Long hotelId) {

        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow();
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow();
        Set <Room_type> room_types = hotel.getRoom_types();

        for (Room_type r : room_types) {

            if
                    (r.getRoom_type_id()
                    .equals(reservation.getRoom_type_id().getRoom_type_id())
            ) {

                Set <Reservation> reservationSet = r.getReservations();
                reservationSet.removeIf(rs -> rs.getId().equals(reservation.getId()));
                reservationRepository.delete(reservation);
                break;
            }
        }
        hotelRepository.save(hotel);

    }

    // does not show in advance if some room type is unavailable for check in date <-- add it later
    @PostMapping("/createReservation") // no return
    public Long createReservation (@RequestParam String userName,
                                   @RequestParam Long hotelId,
                                   @RequestParam String checkInDate,
                                   @RequestParam String checkOutDate,
                                   @RequestParam int prePaidPrice,
                                   @RequestParam int roomCount,
                                   @RequestParam Long room_typeId) throws ParseException {

        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow();
        Set <Room_type> room_types = hotel.getRoom_types();
        User cur_guest = guestRepository.findByUsername(userName).orElseThrow();

        Reservation reservation = new Reservation(
                new SimpleDateFormat("yyyy-MM-dd").parse(checkInDate),
                new SimpleDateFormat("yyyy-MM-dd").parse(checkOutDate),
                prePaidPrice, roomCount, cur_guest
        );

        reservation.setUser_id(cur_guest);

        for (Room_type r : room_types) {

            if
            (r.getRoom_type_id()
                    .equals(room_typeId)
            ) {

                r.getReservations().add(reservation);
                reservation.setRoom_type_id(r);
                break;
            }
        }

        guestRepository.save(cur_guest);

        hotelRepository.save(hotel);
        reservationRepository.save(reservation);

        return reservation.getId();
    }

}
