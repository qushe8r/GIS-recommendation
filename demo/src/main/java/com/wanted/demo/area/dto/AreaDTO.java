package com.wanted.demo.area.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AreaDTO {
    private Long id;
    private String doSi;
    private String sgg;
    private Double longitude;
    private Double latitude;

    public AreaDTO(Long id, String doSi, String sgg) {
        this.id = id;
        this.doSi = doSi;
        this.sgg = sgg;
    }
    public AreaDTO(Long id, String doSi, String sgg, Double longitude, Double latitude) {
        this.id = id;
        this.doSi = doSi;
        this.sgg = sgg;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
