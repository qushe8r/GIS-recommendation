package com.wanted.demo.store.dto;

import lombok.Getter;

@Getter
public class ReviewDTO {
    private Long id;
    private int rating;
    private String evaluation;
    private String userName;

    public ReviewDTO(Long id, int rating, String evaluation, String userName) {
        this.id = id;
        this.rating = rating;
        this.evaluation = evaluation;
        this.userName = userName;
    }
}
