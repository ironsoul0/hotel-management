package com.example.demo.controller;

import com.example.demo.model.Room;
import com.example.demo.repository.HotelRepository;
import com.example.demo.model.Hotel;
import com.example.demo.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @PostMapping("/rooms/add")
    public Room roomsPostAdd(@RequestParam Integer number, @RequestParam Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow();
        Room room = new Room(number, hotel);
        return roomRepository.save(room);
    }

    @GetMapping("/rooms")
    public List<Room> findAllRooms() {
        return (List<Room>) roomRepository.findAll();
    }

    @GetMapping("/rooms/allHotelRooms")
    public Set<Room> findAllHotelRooms(@RequestParam Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow();
        return hotel.getRooms();
    }

    @PostMapping("/rooms/occupy")
    public Room occupyRoom(@RequestParam Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow();
        room.setOccupied(true);
        return roomRepository.save(room);
    }
}
