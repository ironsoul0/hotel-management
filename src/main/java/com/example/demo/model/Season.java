package com.example.demo.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "seasons")
public class Season {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    @Column(name = "season_name" ,unique = true)
    private String seasonName;

    @NotBlank
    @Size(max = 20)
    private String startDate;

    @NotBlank
    @Size(max = 20)
    private String endDate;

    @OneToMany (mappedBy = "season", cascade = CascadeType.ALL)
    private Set <TakesPlaceIn> takesPlaceIns; // sorry... I know, it looks weird and ugly

    public Season () {}

    public Season (String seasonName, String startDate, String endDate) {

        this.seasonName = seasonName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getSeasonName () {
        return seasonName;
    }

    public void setSeasonName (String seasonName) { this.seasonName = seasonName; }

    public String getStartDate () {
        return startDate;
    }

    public void setStartDate (String startDate) { this.startDate = startDate; }

    public String getEndDate () {
        return endDate;
    }

    public void setEndDate (String endDate) { this.endDate = endDate; }

    public Set <TakesPlaceIn> getTakesPlaceIns () { return takesPlaceIns; }

    public void setTakesPlaceIns (Set <TakesPlaceIn> takesPlaceIns) { this.takesPlaceIns = takesPlaceIns; }
}
