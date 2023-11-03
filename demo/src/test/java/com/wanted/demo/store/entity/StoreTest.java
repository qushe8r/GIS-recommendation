package com.wanted.demo.store.entity;

import com.wanted.demo.domain.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Random;
import java.util.stream.IntStream;

class StoreTest {

    Store store = new Store(1L);

    int rating1 = new Random().nextInt(5) + 1;
    int rating2 = new Random().nextInt(5) + 1;
    int rating3 = new Random().nextInt(5) + 1;
    int rating4 = new Random().nextInt(5) + 1;
    int rating5 = new Random().nextInt(5) + 1;
    int rating6 = new Random().nextInt(5) + 1;
    int rating7 = new Random().nextInt(5) + 1;

    Review review1 = new Review(rating1, "eval#1", new User(1L), store);
    Review review2 = new Review(rating2, "eval#1", new User(2L), store);
    Review review3 = new Review(rating3, "eval#1", new User(3L), store);
    Review review4 = new Review(rating4, "eval#1", new User(4L), store);
    Review review5 = new Review(rating5, "eval#1", new User(5L), store);
    Review review6 = new Review(rating6, "eval#1", new User(6L), store);
    Review review7 = new Review(rating7, "eval#1", new User(7L), store);

    @DisplayName("평점 계산 반복 테스트")
    @RepeatedTest(value = 3, name = "@repeatedTest, {currentRepetition}/{totalRepetitions}")
    void calculateRating() {
        // given
        setReviews();
        store.calculateRating();
        // when
        double average = IntStream.of(rating1, rating2, rating3, rating4, rating5, rating6, rating7)
                .average().orElse(0D);
        // then
        Assertions.assertThat(average).isNotZero();
        Assertions.assertThat(store.getRating()).isEqualTo(average);
    }

    private void setReviews() {
        store.receives(review1);
        store.receives(review2);
        store.receives(review3);
        store.receives(review4);
        store.receives(review5);
        store.receives(review6);
        store.receives(review7);
    }
}