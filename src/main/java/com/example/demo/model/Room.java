package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "occupied", nullable = false)
    private boolean occupied;

    @Column(name = "number", nullable = false)
    private Integer number;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hotel_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_type_id")                          // idk what column name should be here
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Room_type room_type;

    public Room() {
    }

    public Room(Integer number, Hotel hotel, Room_type room_type) {
        this.occupied = false;
        this.number = number;
        this.hotel = hotel;
        this.room_type = room_type;
    }

    public Room(Integer number, Hotel hotel, Room_type room_type, boolean occupied) {
        this.occupied = occupied;
        this.number = number;
        this.hotel = hotel;
        this.room_type = room_type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getOccupied() {
        return occupied;
    }

    public void setOccupied(Boolean occupied) {
        this.occupied = occupied;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Room_type getRoom_type () { return room_type; }
}
