package com.wanted.demo.store.entity;

import com.wanted.demo.store.dto.api_response.StoreRawData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@NoArgsConstructor
@Getter
@Entity
@Table(name = "store", indexes = {
        @Index(name = "idx_store_id_idx", columnList = "store_id")
})
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "store_id", unique = true)
    private String storeId;

    private String sigunNm;

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

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @Version
    private Integer version;

    public Store(StoreRawData rawData) {
        this.storeId = (rawData.bizplcNm() + rawData.refineLotnoAddr()).replaceAll("\\s", "");
        this.sigunNm = preProcessingStringRawDataDefault(rawData.sigunNm());
        this.bizplcNm = preProcessingStringRawDataDefault(rawData.bizplcNm());
        String rawLicensgDe = rawData.licensgDe();
        if (rawLicensgDe.length() == 6) {
            rawLicensgDe = rawLicensgDe + "01";
        }
        if (rawLicensgDe.length() == 8) {
            String convertedRawLicensgDe = rawLicensgDe.substring(0, 4) + "-" + rawLicensgDe.substring(4, 6) + "-" + rawLicensgDe.substring(6, 8);
            this.licensgDe = LocalDate.parse(convertedRawLicensgDe);
        } else {
            this.licensgDe = LocalDate.parse(rawLicensgDe);
        }
        this.bsnStateNm = preProcessingStringRawDataDefault(rawData.bsnStateNm());
        String rawClsBizDe = rawData.clsbizDe();
        if (rawClsBizDe == null || rawClsBizDe.isEmpty()) {
            this.clsbizDe = "open";
        } else {
            this.clsbizDe = "close";
        }
        this.locplcAr = preProcessingStringRawDataToDouble(rawData.locplcAr());
        this.gradFacltDivNm = preProcessingStringRawDataDefault(rawData.gradFacltDivNm());
        this.maleEnflpsnCnt = preProcessingStringRawDataToInteger(rawData.maleEnflpsnCnt());
        this.yy = preProcessingStringRawDataToInteger(rawData.yy());
        this.multiUseBizestblYn = preProcessingStringRawDataDefault(rawData.multiUseBizestblYn());
        this.gradDivNm = preProcessingStringRawDataDefault(rawData.gradDivNm());
        this.totFacltScale = preProcessingStringRawDataToDouble(rawData.totFacltScale());
        this.femaleEnflpsnCnt = preProcessingStringRawDataToInteger(rawData.femaleEnflpsnCnt());
        this.bsnsiteCircumfrDivNm = preProcessingStringRawDataDefault(rawData.bsnsiteCircumfrDivNm());
        this.sanittnIndutypeNm = preProcessingStringRawDataDefault(rawData.sanittnIndutypeNm());
        this.sanittnBizcondNm = preProcessingStringRawDataDefault(rawData.sanittnBizcondNm());
        this.totEmplyCnt = preProcessingStringRawDataToDouble(rawData.totEmplyCnt());
        this.refineRoadnmAddr = preProcessingStringRawDataDefault(rawData.refineRoadnmAddr());
        this.refineLotnoAddr = preProcessingStringRawDataDefault(rawData.refineLotnoAddr());
        this.refineZipCd = preProcessingStringRawDataDefault(rawData.refineZipCd());
        this.refineWgs84Logt = preProcessingStringRawDataToDouble(rawData.refineWgs84Logt());
        this.refineWgs84Lat = preProcessingStringRawDataToDouble(rawData.refineWgs84Lat());
        this.rating = 0D;
    }

    private String preProcessingStringRawDataDefault(String field) {
        if (field == null || field.isEmpty()) {
            return "no info";
        } else {
            return field;
        }
    }

    private Double preProcessingStringRawDataToDouble(String field) {
        if (field == null || field.isEmpty()) {
            return 0D;
        } else {
            return Double.valueOf(field);
        }
    }

    private Integer preProcessingStringRawDataToInteger(String field) {
        if (field == null || field.isEmpty()) {
            return 0;
        } else {
            return Integer.valueOf(field);
        }
    }

    public void update(Store store) {
        this.storeId = store.getStoreId();
        this.sigunNm = store.getSigunNm();
        this.bizplcNm = store.getBizplcNm();
        this.licensgDe = store.getLicensgDe();
        this.bsnStateNm = store.getBsnStateNm();
        this.clsbizDe = store.getClsbizDe();
        this.locplcAr = store.getLocplcAr();
        this.gradFacltDivNm = store.getGradFacltDivNm();
        this.maleEnflpsnCnt = store.getMaleEnflpsnCnt();
        this.yy = store.getYy();
        this.multiUseBizestblYn = store.getMultiUseBizestblYn();
        this.gradDivNm = store.getGradDivNm();
        this.totFacltScale = store.getTotFacltScale();
        this.femaleEnflpsnCnt = store.getFemaleEnflpsnCnt();
        this.bsnsiteCircumfrDivNm = store.getBsnsiteCircumfrDivNm();
        this.sanittnIndutypeNm = store.getSanittnIndutypeNm();
        this.sanittnBizcondNm = store.getSanittnBizcondNm();
        this.totEmplyCnt = store.getTotEmplyCnt();
        this.refineRoadnmAddr = store.getRefineRoadnmAddr();
        this.refineLotnoAddr = store.getRefineLotnoAddr();
        this.refineZipCd = store.getRefineZipCd();
        this.refineWgs84Lat = store.getRefineWgs84Lat();
        this.refineWgs84Logt = store.getRefineWgs84Logt();
    }

    public Store(Long id) {
        this.id = id;
    }

    public void calculateRating() {
        this.rating = this.reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0D);
    }

    public void receives(Review review) {
        this.reviews.add(review);
        if (review.getStore() != this) {
            review.about(this);
        }
    }

    public void versioning() {
        this.version++;
    }
}
