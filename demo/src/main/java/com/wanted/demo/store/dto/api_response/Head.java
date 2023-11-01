package com.wanted.demo.store.dto.api_response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Head(
        @JsonProperty("list_total_count")
        Integer listTotalCount,

        @JsonProperty("RESULT")
        Result result,

        @JsonProperty("api_version")
        String apiVersion
) {
}
