package com.wanted.demo.store.dto.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wanted.demo.domain.user.domain.User;
import com.wanted.demo.store.entity.Review;

public record ReviewPost(
        @JsonProperty("rating")
        int rating,

        @JsonProperty("evaluation")
        String evaluation
) {

    public Review toEntity(Long userId) {
        return new Review(
                rating,
                evaluation,
                new User(userId),
                null
        );
    }
}
