package com.wanted.demo.store.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class StoreDetailDTO {
    private Long id;
    private String storeId;
    private String bizplcNm;
    private LocalDate licensgDe;
    private String bsnStateNm;
    private String clsbizDe;
    private Double locplcAr;
    private String gradFacltDivNm;
    private Integer maleEnflpsnCnt;
    private Integer yy;
    private String multiUseBizestblYn;
    private String gradDivNm;
    private Double totFacltScale;
    private Integer femaleEnflpsnCnt;
    private String bsnsiteCircumfrDivNm;
    private String sanittnIndutypeNm;
    private String sanittnBizcondNm;
    private Double totEmplyCnt;
    private String refineRoadnmAddr;
    private String refineLotnoAddr;
    private String refineZipCd;
    private Double refineWgs84Lat;
    private Double refineWgs84Logt;
    private Double rating;
    private List<ReviewDTO> reviews;
}
