package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

@Controller
public class HotelDebugController {

    @Autowired
    private HotelRepository hotelrepo;

    @Autowired
    private Room_typeRepository roomtyperepo;

    @Autowired
    private RoomRepository roomrepo;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeWorkingHoursRepository employeeWorkingHoursRepository;

    @Autowired
    private PasswordEncoder encoder;



    @GetMapping("/debug/populate")
    public String populate(Model model){
        return "redirect:/debug/populate-hotels";
    }

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
//        hotelrepo.deleteAll();
//        Hotel a = new Hotel("Radison Blue", "Astana", "+77718255241", "Pool, bar, Wi-Fi");
//        Hotel b = new Hotel("Sky Lion", "Astana", "+77712158212", "Pool, Wi-Fi, Tennis Court");
//        Hotel c = new Hotel("Harajuku Plaza", "Almaty", "+77011212121", "Pool, Fashion Hall, Wi-Fi");
//        Hotel d = new Hotel("Sky Castle", "Almaty", "+77718878764", "Wi-Fi, Open Roof, Free lunch");
//
//        hotelrepo.save(a);
//        hotelrepo.save(b);
//        hotelrepo.save(c);
//        hotelrepo.save(d);
//
//        return "redirect:/debug/populate-roomtypes";
        hotelrepo.deleteAll();

        Hotel almaty1 = new Hotel("Kazakhstan Hotel", "Almaty", "+77718255241", "City view, Mountain view, Quiet street view, Landmark view", "" +
                "Overlooking Almaty and the Alatau Mountains, the rooms and suites at the 4-star Kazakhstan Hotel feature satellite TV, air conditioning, and soundproofed windows.");

        Hotel almaty2 = new Hotel("Almaty Hotel", "Almaty", "+77777777777", "Spa, Non-smoking rooms, Restaurant, Room service", "" +
                "Located in the heart of Almaty city, this hotel comes with a restaurant, sauna and massage service. It features a 24-hour reception and free Wi-Fi." +
                "Decorated in modern style, rooms at Almaty Hotel are equipped with a mini-bar, flat-screen TV and fridge." +
                "Guests can enjoy European, Italian and Japanese cuisine in Issyk restaurant, or relax with a cocktail at the bar.");

        Hotel astana1 = new Hotel("Rixos President Hotel Astana", "Astana", "+777777777", "swimming pool, airport shuttle, restaurant", "Located in Astana’s government district, this 5-star hotel has 2 elegant restaurants and a large Anjana SPA with a swimming pool. Its air-conditioned rooms and suites include a spa bath and free WiFi." +
                "The bright rooms and suites of the Rixos President Astana also feature flat-screen TVs and in-room safety deposit boxes. The hotel features free international phone calls.");

        Hotel astana2 = new Hotel("Hampton By Hilton Astana Triumphal Arch", "Astana", "+7777777777","Airport shuttle, Free Wifi, Fitness center","Hampton By Hilton Astana Triumphal Arch is located in the business district of Astana. A 17-minute walk from Expo 2017 Astana, the property is also 1.9 mi away from Bayterek Monument. The hotel features a fitness center and a 24-hour front desk.");

        Hotel london1 = new Hotel("Park Plaza Westminster Bridge London", "London", "+95465421321", "Restaurant, Bar, Spa", "Located on the South Bank of the Thames, Park Plaza Westminster Bridge London is set opposite the Houses of Parliament and Big Ben, on the South Bank. It is less than a 5-minute walk from the London Eye, the Aquarium, restaurants and theaters.");

        Hotel london2 = new Hotel("Park Plaza County Hall London", "London", "+9435645613", "Spa, Fitness Center, Bar", "Located on London's South Bank, this modern and family-friendly hotel is just a few minutes’ walk from the River Thames and London Eye. The Park Plaza County Hall London offers a bar, gym, and a restaurant.");

        Hotel munich1 = new Hotel("Maritim Hotel München", "Munich", "+9565421354", "Bar, Good Breakfast, Non-smoking rooms", "A large indoor pool with panoramic rooftop views of Munich, an international restaurant and air-conditioned rooms are offered at this hotel. Maritim Hotel München is centrally located, just 300 m away from Munich Main Station.");

        Hotel munich2 = new Hotel("Vier Jahreszeiten Kempinski München", "Munich", "+946541231456", "Spa, Bar, Restaurant", "An elegant spa with pool and panoramic city views are featured at this 5-star hotel. It is centrally located in Munich, a 5-minute walk from Marienplatz Squar");

        hotelrepo.save(almaty1);
        hotelrepo.save(almaty2);
        hotelrepo.save(astana1);
        hotelrepo.save(astana2);
        hotelrepo.save(london1);
        hotelrepo.save(london2);
        hotelrepo.save(munich1);
        hotelrepo.save(munich2);

        return "redirect:/debug/populate-roomtypes";
    }

    @GetMapping("/debug/populate-roomtypes")
    public String populateRoomTypes(Model model) throws ParseException {
        roomtyperepo.deleteAll();
        roomrepo.deleteAll();
        employeeRepository.deleteAll();
        employeeWorkingHoursRepository.deleteAll();
        Iterable<Hotel> lst = hotelrepo.findAll();

        lst.forEach((hotel) -> {

            if (hotel.getName().equals("Kazakhstan Hotel")) {
                Room_type a = new Room_type("Standard Double", hotel, 2, 25, "Mountain view, Air Conditioning, Flat-screen TV, Minibar", 45000);
                Room_type b = new Room_type("Queen Suite", hotel, 4, 47, "Soundproof, Coffee machine, Landmark view", 88200);
                Room_type c = new Room_type("Presedential Suite", hotel, 2, 64, "Mountain view, City view, Minibar, Spa tub", 162000);

                roomtyperepo.save(a);
                roomtyperepo.save(b);
                roomtyperepo.save(c);

                Room room1 = new Room( 201, hotel, a);
                Room room2 = new Room( 202, hotel, a);


                Room room3 = new Room( 302, hotel, b);
                Room room4 = new Room( 301, hotel, b);

                Room room5 = new Room( 402, hotel, c);
                Room room6 = new Room( 401, hotel, c);

                roomrepo.save(room1);
                roomrepo.save(room2);
                roomrepo.save(room3);
                roomrepo.save(room4);
                roomrepo.save(room5);
                roomrepo.save(room6);

                Employee e1 = new Employee("sherkhan", "sherkhanazim@gmail.com", "Sherkhan", "Azimov", encoder.encode("sherkhan"), "+7 77541111", 7000, "deskclerk", hotel);
                Employee e2 = new Employee("rustem", "rustem", "Rustem", "Turtayev", encoder.encode("rustem"), "+7 56123", 6000, "deskclerk", hotel);

                employeeRepository.save(e1);
                employeeRepository.save(e2);

                try {
                    EmployeeWorkingHours ew11 = new EmployeeWorkingHours(e1, "06:00", "12:00",  new SimpleDateFormat("yyyy-MM-dd").parse("2020-11-15"), 6);
                    employeeWorkingHoursRepository.save(ew11);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    EmployeeWorkingHours ew12 = new EmployeeWorkingHours(e1, "12:00", "20:00", new SimpleDateFormat("yyyy-MM-dd").parse("2020-11-16"), 8);
                    employeeWorkingHoursRepository.save(ew12);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    EmployeeWorkingHours ew21 = new EmployeeWorkingHours(e2, "08:00", "14:00",  new SimpleDateFormat("yyyy-MM-dd").parse("2020-11-15"), 6);
                    employeeWorkingHoursRepository.save(ew21);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    EmployeeWorkingHours ew22 = new EmployeeWorkingHours(e2, "10:00", "20:00", new SimpleDateFormat("yyyy-MM-dd").parse("2020-11-16"), 8);
                    employeeWorkingHoursRepository.save(ew22);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }

            if (hotel.getName().equals("Almaty Hotel")) {
                Room_type a = new Room_type("Superior Double Room", hotel, 2, 25, "Mountain view, Air Conditioning, Flat-screen TV, Minibar", 50000);
                Room_type b = new Room_type("Premium Suite", hotel, 3, 36, "Soundproof, Coffee machine, Landmark view", 70000);
                Room_type c = new Room_type("Deluxe Single Room", hotel, 1, 30, "Mountain view, City view, Minibar, Spa tub", 40000);

                roomtyperepo.save(a);
                roomtyperepo.save(b);
                roomtyperepo.save(c);

                Room room1 = new Room( 201, hotel, a, false);
                Room room2 = new Room( 202, hotel, a, false);


                Room room3 = new Room( 302, hotel, b, false);
                Room room4 = new Room( 301, hotel, b, false);

                Room room5 = new Room( 402, hotel, c, false);
                Room room6 = new Room( 401, hotel, c, false);

                roomrepo.save(room1);
                roomrepo.save(room2);
                roomrepo.save(room3);
                roomrepo.save(room4);
                roomrepo.save(room5);
                roomrepo.save(room6);

                Employee e1 = new Employee("danel", "danel", "Danel", "Batyrbek", encoder.encode("danel"), "+7 715541111", 5500, "deskclerk", hotel);

                employeeRepository.save(e1);

                try {
                    EmployeeWorkingHours ew11 = new EmployeeWorkingHours(e1, "06:00", "12:00",  new SimpleDateFormat("yyyy-MM-dd").parse("2020-10-15"), 6);
                    employeeWorkingHoursRepository.save(ew11);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    EmployeeWorkingHours ew12 = new EmployeeWorkingHours(e1, "12:00", "20:00", new SimpleDateFormat("yyyy-MM-dd").parse("2020-10-16"), 8);
                    employeeWorkingHoursRepository.save(ew12);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    EmployeeWorkingHours ew21 = new EmployeeWorkingHours(e1, "08:00", "14:00",  new SimpleDateFormat("yyyy-MM-dd").parse("2020-10-15"), 6);
                    employeeWorkingHoursRepository.save(ew21);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    EmployeeWorkingHours ew22 = new EmployeeWorkingHours(e1, "10:00", "20:00", new SimpleDateFormat("yyyy-MM-dd").parse("2020-10-16"), 8);
                    employeeWorkingHoursRepository.save(ew22);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            if (hotel.getName().equals("Rixos President Hotel Astana")) {
                Room_type a = new Room_type("Deluxe Twin Room", hotel, 2, 32, "Mountain view, Air Conditioning, Flat-screen TV, Minibar", 114000);
                Room_type b = new Room_type("Premium King Room", hotel, 2, 30, "Soundproof, Coffee machine, Landmark view", 134000);
                Room_type c = new Room_type("Premium Twin Room", hotel, 2, 30, "Mountain view, City view, Minibar, Spa tub", 150000);

                roomtyperepo.save(a);
                roomtyperepo.save(b);
                roomtyperepo.save(c);

                Room room1 = new Room( 201, hotel, a, false);
                Room room2 = new Room( 202, hotel, a, false);


                Room room3 = new Room( 302, hotel, b, false);
                Room room4 = new Room( 301, hotel, b, false);

                Room room5 = new Room( 402, hotel, c, false);
                Room room6 = new Room( 401, hotel, c, false);

                roomrepo.save(room1);
                roomrepo.save(room2);
                roomrepo.save(room3);
                roomrepo.save(room4);
                roomrepo.save(room5);
                roomrepo.save(room6);
                Employee e1 = new Employee("temirzhan", "temirzhan", "Temirzhan", "Yussupov", encoder.encode("temirzhan"), "+7 775113111", 6000, "deskclerk", hotel);

                employeeRepository.save(e1);

                try {
                    EmployeeWorkingHours ew11 = new EmployeeWorkingHours(e1, "06:00", "12:00",  new SimpleDateFormat("yyyy-MM-dd").parse("2020-12-15"), 6);
                    employeeWorkingHoursRepository.save(ew11);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    EmployeeWorkingHours ew12 = new EmployeeWorkingHours(e1, "12:00", "20:00", new SimpleDateFormat("yyyy-MM-dd").parse("2020-12-16"), 8);
                    employeeWorkingHoursRepository.save(ew12);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    EmployeeWorkingHours ew21 = new EmployeeWorkingHours(e1, "08:00", "14:00",  new SimpleDateFormat("yyyy-MM-dd").parse("2020-12-15"), 6);
                    employeeWorkingHoursRepository.save(ew21);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    EmployeeWorkingHours ew22 = new EmployeeWorkingHours(e1, "10:00", "20:00", new SimpleDateFormat("yyyy-MM-dd").parse("2020-12-16"), 8);
                    employeeWorkingHoursRepository.save(ew22);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            if (hotel.getName().equals("Hampton By Hilton Astana Triumphal Arch")) {
                Room_type a = new Room_type("Queen Room", hotel, 2, 21, "Mountain view, Air Conditioning, Flat-screen TV, Minibar", 36300);
                Room_type b = new Room_type("Twin Room", hotel, 2, 21, "Soundproof, Coffee machine, Landmark view", 36300);
                Room_type c = new Room_type("Superior Queen Room", hotel, 2, 21, "Mountain view, City view, Minibar, Spa tub", 36500);

                roomtyperepo.save(a);
                roomtyperepo.save(b);
                roomtyperepo.save(c);

                Room room1 = new Room( 201, hotel, a, false);
                Room room2 = new Room( 202, hotel, a, false);


                Room room3 = new Room( 302, hotel, b, false);
                Room room4 = new Room( 301, hotel, b, false);

                Room room5 = new Room( 402, hotel, c, false);
                Room room6 = new Room( 401, hotel, c, false);

                roomrepo.save(room1);
                roomrepo.save(room2);
                roomrepo.save(room3);
                roomrepo.save(room4);
                roomrepo.save(room5);
                roomrepo.save(room6);
            }

            if (hotel.getName().equals("Park Plaza Westminster Bridge London")) {
                Room_type a = new Room_type("Double Room", hotel, 2, 21, "Mountain view, Air Conditioning, Flat-screen TV, Minibar", 135000);
                Room_type b = new Room_type("Studio King", hotel, 2, 34, "Soundproof, Coffee machine, Landmark view", 188000);
                Room_type c = new Room_type("Studio Triple", hotel, 3, 45, "Mountain view, City view, Minibar, Spa tub", 216000);

                roomtyperepo.save(a);
                roomtyperepo.save(b);
                roomtyperepo.save(c);

                Room room1 = new Room( 201, hotel, a, false);


                Room room3 = new Room( 302, hotel, b, false);

                Room room5 = new Room( 402, hotel, c, false);

                roomrepo.save(room1);
                roomrepo.save(room3);
                roomrepo.save(room5);
            }

            if (hotel.getName().equals("Park Plaza County Hall London")) {
                Room_type a = new Room_type("Double Room", hotel, 2, 20, "Mountain view, Air Conditioning, Flat-screen TV, Minibar", 135000);
                Room_type b = new Room_type("Twin King", hotel, 2, 20, "Soundproof, Coffee machine, Landmark view", 150000);
                Room_type c = new Room_type("Studio Double", hotel, 3, 30, "Mountain view, City view, Minibar, Spa tub", 170000);

                roomtyperepo.save(a);
                roomtyperepo.save(b);
                roomtyperepo.save(c);

                Room room1 = new Room( 201, hotel, a, false);
                Room room3 = new Room( 302, hotel, b, false);
                Room room5 = new Room( 402, hotel, c, false);

                roomrepo.save(room1);
                roomrepo.save(room3);
                roomrepo.save(room5);
            }

            if (hotel.getName().equals("Maritim Hotel München")) {
                Room_type a = new Room_type("Double Classic", hotel, 2, 20, "Mountain view, Air Conditioning, Flat-screen TV, Minibar", 100000);
                Room_type b = new Room_type("Comfort Double", hotel, 2, 20, "Soundproof, Coffee machine, Landmark view", 120000);
                Room_type c = new Room_type("Comfort Family", hotel, 4, 30, "Mountain view, City view, Minibar, Spa tub", 175000);

                roomtyperepo.save(a);
                roomtyperepo.save(b);
                roomtyperepo.save(c);
                Room room1 = new Room( 201, hotel, a, true);
                Room room3 = new Room( 302, hotel, b, true);
                Room room5 = new Room( 402, hotel, c, true);

                roomrepo.save(room1);
                roomrepo.save(room3);
                roomrepo.save(room5);
            }

            if (hotel.getName().equals("Vier Jahreszeiten Kempinski München")) {
                Room_type a = new Room_type("Double Deluxe", hotel, 2, 35, "Mountain view, Air Conditioning, Flat-screen TV, Minibar", 360000);
                Room_type b = new Room_type("Grand Deluxe", hotel, 3, 35, "Soundproof, Coffee machine, Landmark view", 480000);
                Room_type c = new Room_type("Junior Suite", hotel, 3, 50, "Mountain view, City view, Minibar, Spa tub", 700000);

                roomtyperepo.save(a);
                roomtyperepo.save(b);
                roomtyperepo.save(c);
                Room room1 = new Room( 201, hotel, a, true);
                Room room3 = new Room( 302, hotel, b, true);
                Room room5 = new Room( 402, hotel, c, true);

                roomrepo.save(room1);
                roomrepo.save(room3);
                roomrepo.save(room5);
            }
        });

        return "redirect:/debug/populate-rooms";
    }

    @GetMapping("/debug/populate-rooms")
    public String populateRooms(Model model){

        return "redirect:/debug/test-hotels";
    }

    @GetMapping("/debug/test-hotels")
    public String showHotels(Model model){
        Iterable<Hotel> lst = hotelrepo.findAll();
        model.addAttribute("hotels", lst);
        return "debug-show-hotels";
    }
}
