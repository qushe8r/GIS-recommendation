package com.wanted.demo.store.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoreListDTO {
    private Long id;
    private String storeId;
    private String bizplcNm;
    private Double rating;
    private Double refineWgs84Lat;
    private Double refineWgs84Logt;
    private Double distance;

    public StoreListDTO(Long id, String storeId, String bizplcNm, Double rating, Double refineWgs84Lat, Double refineWgs84Logt, Double distance) {
        this.id = id;
        this.storeId = storeId;
        this.bizplcNm = bizplcNm;
        this.rating = rating;
        this.refineWgs84Lat = refineWgs84Lat;
        this.refineWgs84Logt = refineWgs84Logt;
        this.distance = distance;
    }
}
