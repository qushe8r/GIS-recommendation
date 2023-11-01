package com.wanted.demo.store.dto.api_response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record GenrestrtFood(
        @JsonProperty("head")
        List<Head> head,

        @JsonProperty("row")
        List<StoreRawData> row
) {
}
