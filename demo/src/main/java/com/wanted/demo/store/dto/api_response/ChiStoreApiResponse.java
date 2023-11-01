package com.wanted.demo.store.dto.api_response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ChiStoreApiResponse(

        @JsonProperty("Genrestrtchifood")
        List<GenrestrtFood> genrestrtChiFood
) {
}
