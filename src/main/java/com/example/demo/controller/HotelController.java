package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.Room_typeRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Controller
public class  HotelController {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private Room_typeRepository room_typeRepository;

    @Autowired
    private UserRepository userRepository;

//    @GetMapping("/")
//    public String hotelsMain(Model model) {
//        Iterable<Hotel> hotels = hotelRepository.findAll();
//        model.addAttribute("hotels", hotels);
//        return "home";
//    }

    private String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @GetMapping("/hotels-main")
    public String hotelsMain(Model model) {
        return "hotels-main";
    }

    @GetMapping("/hotels/add")
    public String hotelsAdd(Model model) {
        return "hotels-add";
    }

    @PostMapping("/hotels/add")
    public String hotelsPostAdd(@RequestParam String name, @RequestParam String address, @RequestParam String phone, Model model) {
        Hotel hotel = new Hotel(name, address, phone, "");
        hotelRepository.save(hotel);
        return "redirect:/hotels";
    }

    @GetMapping("/hotels/{id}")
    public String hotelsDetails(@PathVariable(value = "id") long id, Model model) {
        if(!hotelRepository.existsById(id)) {
            return "redirect:/hotels";
        }

        Optional<Hotel> hotel = hotelRepository.findById(id);
        ArrayList<Hotel> res = new ArrayList<>();
        hotel.ifPresent(res::add);
        model.addAttribute("hotel", res);
        return "hotels-details";
    }

    @GetMapping("/hotels/{id}/edit")
    public String hotelsEdit(@PathVariable(value = "id") long id, Model model) {
        if(!hotelRepository.existsById(id)) {
            return "redirect:/hotels";
        }

        Optional<Hotel> hotel = hotelRepository.findById(id);
        ArrayList<Hotel> res = new ArrayList<>();
        hotel.ifPresent(res::add);
        model.addAttribute("hotel", res);
        return "hotels-edit";
    }

    @PostMapping("/hotels/{id}/edit")
    public String hotelsPostUpdate(@PathVariable(value = "id") long id, @RequestParam String name, @RequestParam String address, @RequestParam String phone, Model model) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow();
        hotel.setName(name);
        hotel.setAddress(address);
        hotel.setPhone(phone);
        hotelRepository.save(hotel);
        return "redirect:/hotels";
    }

    @PostMapping("/hotels/{id}/remove")
    public String hotelsPostDelete(@PathVariable(value = "id") long id, Model model) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow();
        hotelRepository.delete(hotel);

        return "redirect:/hotels";
    }

    @GetMapping("/search-hotels/")
    public String hotelSearch(){
        return "redirect:/hotels-main";
    }

    @GetMapping("/search-hotels/{city}")
    public String hotelSearch(@PathVariable String city, Model model) {
        Iterable<Hotel> hotelsAll = hotelRepository.findAll();
        ArrayList<Hotel> resHotels = new ArrayList<>();
        for (Hotel hotel : hotelsAll) {
            if (hotel.getAddress().toLowerCase().contains(city.toLowerCase())) {
                resHotels.add(hotel);
            }
        }
        System.out.println("Hotel");
        model.addAttribute("hotels", (Iterable<Hotel>)resHotels);
        return "search-hotels";
    }

    @PostMapping("/search-hotels/")
    public String hotelsSearch(@RequestParam String city, @RequestParam String checkInDate, @RequestParam String checkOutDate, @RequestParam String numberOfPeople, Model model) {
        String url = "redirect:/search-hotels/" + city;
        return url;
    }

    @GetMapping("/book-room/{id}")
    public String showBookRoomPage(@PathVariable Long id, Model model){
        Hotel h = hotelRepository.findById(id).get();
        model.addAttribute("hotel", h);
        Set<Room_type> room_types = h.getRoom_types();
        Iterable<Room_type> it = room_types;
        model.addAttribute("roomtypes", it);
        return "make-reservation";
    }

    private long calculatePrice(Date d1, Date d2, int bp){
        long diffInMils = Math.abs(d2.getTime() - d1.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMils, TimeUnit.MILLISECONDS);
        return diff * bp;
    }

    @GetMapping("/test-page")
    public String testPage(Model model){
        return "test-page";
    }

    @GetMapping("/book-room")
    public String fromBookToMain(){
        return "redirect:/";
    }

    @PostMapping("/test-page")
    public String testPagePost(Model model){
        return "test-page";
    }

    @PostMapping("/confirm-price")
    public String confirmPrice(Model model){
        return "confirm-price";
    }

    @PostMapping("book-room-real")
    public String bookRoomReal(@RequestParam Long roomtype, @RequestParam String myDate, @RequestParam String myDate2, Model model) throws ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(myDate);
        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(myDate2);
        User u = userRepository.findByUsername(getUsername()).get();
        Room_type rt = room_typeRepository.findById(roomtype).get();
        long price = calculatePrice(date1, date2, rt.getBase_price());
        Reservation r = new Reservation(date1, date2, (int) price, 1, u, false);
        r.setRoom_type_id(rt);
        reservationRepository.save(r);
        return "redirect:/";
    }

    @PostMapping("/book-room")
    public String bookRoom(@RequestParam Long roomtype, @RequestParam String myDate, @RequestParam String myDate2, Model model) throws ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(myDate);
        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(myDate2);
        Room_type rt = room_typeRepository.findById(roomtype).get();
        long price = calculatePrice(date1, date2, rt.getBase_price());
        model.addAttribute("name", "Oracle");
        model.addAttribute("price", price);
        model.addAttribute("date1", myDate);
        model.addAttribute("date2", myDate2);
        model.addAttribute("roomtype", roomtype);
        return "confirm-price";
    }

    @GetMapping("/confirm-price")
    public String redirectToHotels(){
        return "redirect:/";
    }
}
