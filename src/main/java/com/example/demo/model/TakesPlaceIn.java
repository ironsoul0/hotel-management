package com.example.demo.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name="takes_place_in")
public class TakesPlaceIn { // sorry... I know, it looks weird and ugly... but, you know, we are FROGS!

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn (name = "season_name")
    private Season season;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn (name = "hotel_id")
    private Hotel hotel;

    @NotBlank
    @Size(max = 20)
    private String weekdayPrice;

    public TakesPlaceIn () {}

    public TakesPlaceIn (String weekdayPrice) {

        this.weekdayPrice = weekdayPrice;
    }

    public Season getSeason () { return season; }

    public Hotel getHotel () { return hotel; }

    public String getWeekdayPrice () { return weekdayPrice; }

    public Long getId () { return id; }

    public void setWeekdayPrice (String weekdayPrice) { this.weekdayPrice = weekdayPrice; }

    public void setHotel (Hotel hotel) { this.hotel = hotel; }

    public void setSeason (Season season) { this.season = season; }
}
