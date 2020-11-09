package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Room_type")
public class Room_type {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long room_type_id;

    private String name;
    private int capacity;
    private int size;
    private String features;
    private int base_price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hotel_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Hotel hotel_id;

    @OneToMany(mappedBy = "room_type_id", cascade = CascadeType.ALL)
    private Set<Reservation> reservations = new HashSet<>();


    @OneToMany(mappedBy = "room_type", cascade = CascadeType.ALL)
    private Set<Room> rooms = new HashSet<>();

    public Room_type() {

    }

    public Room_type(String name, Hotel hotel_id, Integer capacity, Integer size, String features, int base_price) {
        this.name = name;
        this.hotel_id = hotel_id;
        this.capacity = capacity;
        this.size = size;
        this.features = features;
        this.base_price = base_price;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public Long getRoom_type_id() {
        return room_type_id;
    }

    public void setRoom_type_id(Long room_type_id) {
        this.room_type_id = room_type_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Hotel getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(Hotel hotel_id) {
        this.hotel_id = hotel_id;
    }

    public int getBase_price() {
        return base_price;
    }

    public void setBase_price(int base_price) {
        this.base_price = base_price;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    public Set<Reservation> getReservations() {return reservations;}

}
