package com.example.demo.controller;

import com.example.demo.repository.HotelRepository;
import com.example.demo.model.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class  HotelController {

    @Autowired
    private HotelRepository hotelRepository;

    @GetMapping("/hotels")
    public String hotelsMain(Model model) {
        Iterable<Hotel> hotels = hotelRepository.findAll();
        model.addAttribute("hotels", hotels);
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
}
