package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date checkinDate;
    private Date checkoutDate;
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
    @JoinColumn(name = "user_id_reservation")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user_id;


    public Reservation() {
    }

    public Reservation(Date checkinDate, Date checkoutDate, int prepaid_price, int room_count, User user_id) {
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.prepaid_price = prepaid_price;
        this.room_count = room_count;
        this.user_id = user_id;
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

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }

    public Room_type getRoom_type_id() {
        return room_type_id;
    }

    public void setRoom_type_id(Room_type room_type_id) {
        this.room_type_id = room_type_id;
    }

    public Date getCheckinDate() {
        return this.checkinDate;
    }

    public void setCheckinDate(Date checkinDate) {
        this.checkinDate = checkinDate;
    }

    public Date getCheckoutDate() {
        return this.checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }
}
