package com.example.demo.controller;

import com.example.demo.model.Guest;
import com.example.demo.model.Hotel;
import com.example.demo.model.Reservation;
import com.example.demo.model.Room_type;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.Room_typeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class HotelDebugController {

    @Autowired
    private HotelRepository hotelrepo;

    @Autowired
    private Room_typeRepository roomtyperepo;

    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping("/debug")
    public String printSecret(Model model){
        model.addAttribute("title", "Hello World");
        return "secret";
    }

//    @GetMapping("/debug/populate-reservations")
//    public String populateReservations(Model model) {
//        reservationRepository.deleteAll();
//
//        Reservation a = new Reservation("22.10.1251", "22.10.1251", 1250 , 2);
//
//        reservationRepository.save(a);
//        return "redirect:/reservations";
//    }

    @GetMapping("/debug/populate-hotels")
    public String populateHotels(Model model){
        hotelrepo.deleteAll();
        Hotel a = new Hotel("Radison Blue", "Astana", "+77718255241", "Pool, bar, Wi-Fi");
        Hotel b = new Hotel("Sky Lion", "Astana", "+77712158212", "Pool, Wi-Fi, Tennis Court");
        Hotel c = new Hotel("Harajuku Plaza", "Almaty", "+77011212121", "Pool, Fashion Hall, Wi-Fi");
        Hotel d = new Hotel("Sky Castle", "Almaty", "+77718878764", "Wi-Fi, Open Roof, Free lunch");

        hotelrepo.save(a);
        hotelrepo.save(b);
        hotelrepo.save(c);
        hotelrepo.save(d);

        return "redirect:/debug/test-hotels";
    }

    @GetMapping("/debug/populate-roomtypes")
    public String populateRoomTypes(Model model){
        roomtyperepo.deleteAll();

        Iterable<Hotel> lst = hotelrepo.findAll();

        lst.forEach(hotel -> {
            System.out.println(hotel.getId());
        });
//
//        hotelrepo.findById(new Long(1)).map(hotel -> {
//            b.setHotel(hotel);
//            return b;
//        });
//
//        hotelrepo.findById(new Long(1)).map(hotel -> {
//            c.setHotel(hotel);
//            return c;
//        });
//
//        hotelrepo.findById(new Long(2)).map(hotel -> {
//            d.setHotel(hotel);
//            return d;
//        });
//
//        hotelrepo.findById(new Long(2)).map(hotel -> {
//            e.setHotel(hotel);
//            return e;
//        });
//
//        hotelrepo.findById(new Long(2)).map(hotel -> {
//            f.setHotel(hotel);
//            return f;
//        });
//
//        hotelrepo.findById(new Long(3)).map(hotel -> {
//            g.setHotel(hotel);
//            return g;
//        });
//
//        hotelrepo.findById(new Long(3)).map(hotel -> {
//            h.setHotel(hotel);
//            return h;
//        });
//
//        hotelrepo.findById(new Long(3)).map(hotel -> {
//            i.setHotel(hotel);
//            return i;
//        });
//
//        hotelrepo.findById(new Long(4)).map(hotel -> {
//            a.setHotel(hotel);
//            return l;
//        });
//
//        hotelrepo.findById(new Long(4)).map(hotel -> {
//            b.setHotel(hotel);
//            return k;
//        });
//
//        hotelrepo.findById(new Long(4)).map(hotel -> {
//            c.setHotel(hotel);
//            return l;
//        });

        lst.forEach((hotel) -> {
            System.out.println(hotel.getName());
            Room_type a = new Room_type("Standard", hotel, 50, 1, "TV, Bed");
            Room_type b = new Room_type("HalfLux", hotel, 10, 1, "TV, Bed, Bathroom");
            Room_type c = new Room_type("Lux", hotel, 5, 2, "Big Bed, Wi-Fi, Bathroom");
            roomtyperepo.save(a);
            roomtyperepo.save(b);
            roomtyperepo.save(c);
        });
//        Room_type a = new Room_type("Standard", hotel_a, 50, 1, "TV, Bed");
//        Room_type b = new Room_type("HalfLux", hotel_a, 10, 1, "TV, Bed, Bathroom");
//        Room_type c = new Room_type("Lux", hotel_a, 5, 2, "Big Bed, Wi-Fi, Bathroom");
//
//        Room_type d = new Room_type("Standard", hotel_b, 20, 1, "TV, Bed");
//        Room_type e = new Room_type("HalfLux", hotel_b, 10, 1, "TV, Bed, Bathroom");
//        Room_type f = new Room_type("Lux", hotel_b, 5, 2, "Big Bed, Wi-Fi, Bathroom");
//
//        Room_type g = new Room_type("Standard", hotel_c, 20, 1, "TV, Bed");
//        Room_type h = new Room_type("HalfLux", hotel_c, 10, 1, "TV, Bed, Bathroom");
//        Room_type i = new Room_type("Lux", hotel_c, 5, 2, "Big Bed, Wi-Fi, Bathroom");
//
//        Room_type j = new Room_type("Standard", hotel_d, 20, 1, "TV, Bed");
//        Room_type k = new Room_type("HalfLux", hotel_d, 10, 1, "TV, Bed, Bathroom");
//        Room_type l = new Room_type("Lux", hotel_d, 5, 2, "Big Bed, Wi-Fi, Bathroom");

//        hotelrepo.findById(new Long(2)).map(hotel -> {
//            d.setHotel(hotel);
//            e.setHotel(hotel);
//            f.setHotel(hotel);
//        });
//
//        hotelrepo.findById(new Long(3)).map(hotel -> {
//            g.setHotel(hotel);
//            h.setHotel(hotel);
//            i.setHotel(hotel);
//        });
//
//        hotelrepo.findById(new Long(4)).map(hotel -> {
//            j.setHotel(hotel);
//            k.setHotel(hotel);
//            l.setHotel(hotel);
//        });

        return "redirect:/debug/test-hotels";
    }

    @GetMapping("/debug/test-hotels")
    public String showHotels(Model model){
        Iterable<Hotel> lst = hotelrepo.findAll();
        model.addAttribute("hotels", lst);
        return "debug-show-hotels";
    }
}
