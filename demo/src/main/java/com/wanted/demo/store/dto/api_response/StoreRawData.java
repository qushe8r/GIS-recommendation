package com.wanted.demo.store.dto.api_response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StoreRawData(
        @JsonProperty("SIGUN_NM")
        String sigunNm,

        @JsonProperty("SIGUN_CD")
        String sigunCd,

        @JsonProperty("BIZPLC_NM")
        String bizplcNm,

        @JsonProperty("LICENSG_DE")
        String licensgDe,

        @JsonProperty("BSN_STATE_NM")
        String bsnStateNm,

        @JsonProperty("CLSBIZ_DE")
        String clsbizDe,

        @JsonProperty("LOCPLC_AR")
        String locplcAr,

        @JsonProperty("GRAD_FACLT_DIV_NM")
        String gradFacltDivNm,

        @JsonProperty("MALE_ENFLPSN_CNT")
        String maleEnflpsnCnt,

        @JsonProperty("YY")
        String yy,

        @JsonProperty("MULTI_USE_BIZESTBL_YN")
        String multiUseBizestblYn,

        @JsonProperty("GRAD_DIV_NM")
        String gradDivNm,

        @JsonProperty("TOT_FACLT_SCALE")
        String totFacltScale,

        @JsonProperty("FEMALE_ENFLPSN_CNT")
        String femaleEnflpsnCnt,

        @JsonProperty("BSNSITE_CIRCUMFR_DIV_NM")
        String bsnsiteCircumfrDivNm,

        @JsonProperty("SANITTN_INDUTYPE_NM")
        String sanittnIndutypeNm,

        @JsonProperty("SANITTN_BIZCOND_NM")
        String sanittnBizcondNm,

        @JsonProperty("TOT_EMPLY_CNT")
        String totEmplyCnt,

        @JsonProperty("REFINE_ROADNM_ADDR")
        String refineRoadnmAddr,

        @JsonProperty("REFINE_LOTNO_ADDR")
        String refineLotnoAddr,

        @JsonProperty("REFINE_ZIP_CD")
        String refineZipCd,

        @JsonProperty("REFINE_WGS84_LAT")
        String refineWgs84Lat,

        @JsonProperty("REFINE_WGS84_LOGT")
        String refineWgs84Logt
) {
}
