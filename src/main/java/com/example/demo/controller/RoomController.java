package com.example.demo.controller;

import com.example.demo.model.Room;
import com.example.demo.model.Room_type;
import com.example.demo.repository.HotelRepository;
import com.example.demo.model.Hotel;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.Room_typeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private Room_typeRepository room_typeRepository;

    @PostMapping("/add")
    public Room roomsPostAdd(@RequestParam Integer number, @RequestParam Long hotelId, @RequestParam Long room_typeId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow();
        Room_type room_type = room_typeRepository.findById(room_typeId).orElseThrow();
        Room room = new Room(number, hotel, room_type);
        return roomRepository.save(room);
    }

    @GetMapping("/")
    public List<Room> findAllRooms() {
        return (List<Room>) roomRepository.findAll();
    }

    @GetMapping("/allHotelRooms")
    public Set<Room> findAllHotelRooms(@RequestParam Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow();
        return hotel.getRooms();
    }

    @PostMapping("/occupy")
    public Room occupyRoom(@RequestParam Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow();
        room.setOccupied(true);
        return roomRepository.save(room);
    }
}
