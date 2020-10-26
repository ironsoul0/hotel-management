package com.example.demo.controller;

import com.example.demo.model.Hotel;
import com.example.demo.model.Room_type;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.Room_typeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class Room_typeController {

    @Autowired
    private Room_typeRepository room_typeRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @GetMapping("room_types")
    public List<Room_type> findAllRoom_type(){
        return (List<Room_type>) room_typeRepository.findAll();
    }

    @GetMapping("/room_types/allHotelRoom_types")
    public Set<Room_type> findAllHotelRooms(@RequestParam Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow();
        return hotel.getRoom_types();
    }
}
