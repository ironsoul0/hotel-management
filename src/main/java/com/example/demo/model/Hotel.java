package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "hotel")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private Set<Room> rooms = new HashSet<>();

    @JsonBackReference
    @OneToMany (mappedBy = "hotel", cascade = CascadeType.ALL)
    private Set <TakesPlaceIn> takesPlaceIns; // sorry... I know, it looks weird and ugly

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(mappedBy = "hotel_id", cascade = CascadeType.ALL)
    private Set<Room_type> room_types = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "hotel_id_employee", cascade = CascadeType.ALL)
    private Set<Employee> employees = new HashSet<>();

    private String name;

    private String address;
    private String phone;
    private String features;
    @Lob
    @Column(name="description", length=2048)
    private String description;

    private int views;

    public Hotel() {

    }
    public Hotel(String name, String address, String phone, String features) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.views = 0;
        this.features = features;
        this.description = description;
    }

    public Hotel(String name, String address, String phone, String features, String description) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.views = 0;
        this.features = features;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public Set<Room_type> getRoom_types() {return room_types;}

    public String getFeatures() { return features; }

    public void setFeatures(String features) { this.features = features; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set <TakesPlaceIn> getTakesPlaceIns () { return takesPlaceIns; }

    public void setTakesPlaceIns (Set <TakesPlaceIn> takesPlaceIns) { this.takesPlaceIns = takesPlaceIns; }

}
