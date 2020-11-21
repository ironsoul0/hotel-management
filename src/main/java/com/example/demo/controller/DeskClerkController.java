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

@CrossOrigin(origins = "*", maxAge = 3600)
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

    // parameter and inside code could change depending on front end for desk clerk page
    @GetMapping("/allReserves") // this one works
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
    @PostMapping("/allReserves/{id}/approve") // this one works
    public Set <Long> approveReservation (@PathVariable Long id, @RequestParam Long hotelId) {

        Reservation reservation = reservationRepository.findById(id).orElseThrow();
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow();

        Set <Long> occupiedRoomIds = new HashSet<>();
        Set <Room_type> room_types = hotel.getRoom_types();

        for (Room_type r : room_types) {

            if (r.getRoom_type_id()
                    .equals(reservation.getRoom_type_id()
                            .getRoom_type_id())) {

                Set <Room> rooms = r.getRooms();

                for (Room room : rooms) {

                    if (!room.getOccupied()) {

                        occupiedRoomIds.add(room.getId());
                        room.setOccupied(true);
                    }
                }

                hotelRepository.save(hotel);

                break;
            }
        }

        if (occupiedRoomIds.size() == reservation.getRoom_count()) {

            reservation.setApproved(true);
            reservationRepository.save(reservation);

            return occupiedRoomIds;

        } else {

            Set <Room> rooms = hotel.getRooms();

            for (Long l : occupiedRoomIds) {

                for (Room r : rooms) {

                    if (r.getId().equals(l)) {

                        r.setOccupied(false);
                    }
                }
            }

            hotelRepository.save(hotel);

            occupiedRoomIds.clear();

            reservation.setApproved(false);
            reservationRepository.save(reservation);

            Set <Long> notApprovedReservation = new HashSet<>();
            notApprovedReservation.add((long) -1);

            return notApprovedReservation;
        }
    }

    // this is implemented as a function for button
    // parameter hotelId and inside code could change depending on front end for desk clerk page
    @PostMapping("/allReserves/{id}/delete") // works
    public void removeReservation (@PathVariable Long id, @RequestParam Long hotelId) {

        Reservation reservation = reservationRepository.findById(id).orElseThrow();
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
    @PostMapping("/allReserves/create") // works
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
                prePaidPrice, roomCount, cur_guest, false
        );

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

    // does not show in advance if some room type is unavailable for check in date <-- add it later
    @PostMapping("/allReserves/{id}/edit")
    public Long editReservation (@PathVariable Long id,
                                 @RequestParam Long hotelId,
                                 @RequestParam String checkInDate,
                                 @RequestParam String checkOutDate,
                                 @RequestParam int roomCount,
                                 @RequestParam Long room_typeId) throws ParseException {

        Reservation reservation = reservationRepository.findById(id).orElseThrow();

        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow();
        Set <Room_type> room_types = hotel.getRoom_types();

        if (checkInDate != null) {
            reservation.setCheckinDate(
                    new SimpleDateFormat("yyyy-MM-dd")
                            .parse(checkInDate)
            );
        }

        if (checkOutDate != null) {
            reservation.setCheckoutDate(
                    new SimpleDateFormat("yyyy-MM-dd")
                            .parse(checkOutDate)
            );
        }

        if (room_typeId != null) {
            for (Room_type r : room_types)
                if (r.getRoom_type_id().equals(room_typeId)) {
                    reservation.setRoom_type_id(r);
                    break;
                }
        }

        if (roomCount > 0)
            reservation.setRoom_count(roomCount);

        reservationRepository.save(reservation);
        hotelRepository.save(hotel);

        return id;
    }
}
