package com.wanted.demo.store.dto.api_response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Result(
        @JsonProperty("CODE")
        String code,

        @JsonProperty("MESSAGE")
        String message
) {
}
