package com.wanted.demo.area.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Getter
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String doSi;

    private String sgg;

    private Double longitude;

    private Double latitude;

    public Area(String doSi, String sgg, Double longitude, Double latitude) {
        this.doSi = doSi;
        this.sgg = sgg;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
