package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String checkinDate;
    private String checkoutDate;
    private int prepaid_price;
    private int room_count;

    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_type_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Room_type room_type_id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "guest_id_reservation")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Guest guest_id;


    public Reservation() {
    }

    public Reservation(String checkinDate, String checkoutDate, int prepaid_price, int room_count) {
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.prepaid_price = prepaid_price;
        this.room_count = room_count;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPrepaid_price() {
        return prepaid_price;
    }

    public void setPrepaid_price(int prepaid_price) {
        this.prepaid_price = prepaid_price;
    }

    public int getRoom_count() {
        return room_count;
    }

    public void setRoom_count(int room_count) {
        this.room_count = room_count;
    }

    public Guest getGuest_id() {
        return guest_id;
    }

    public void setGuest_id(Guest guest_id) {
        this.guest_id = guest_id;
    }

    public Room_type getRoom_type_id() {
        return room_type_id;
    }

    public void setRoom_type_id(Room_type room_type_id) {
        this.room_type_id = room_type_id;
    }

    public String getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(String checkinDate) {
        this.checkinDate = checkinDate;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
    }
}
