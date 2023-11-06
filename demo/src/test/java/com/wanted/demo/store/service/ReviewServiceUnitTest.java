package com.wanted.demo.store.service;

import com.wanted.demo.domain.user.domain.User;
import com.wanted.demo.store.dto.review.ReviewPost;
import com.wanted.demo.store.entity.Review;
import com.wanted.demo.store.entity.Store;
import com.wanted.demo.store.repository.StoreRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@Import(ReviewService.class)
class ReviewServiceUnitTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    ReviewService reviewService;

    @MockBean
    StoreRepository storeRepository;

    Store store = new Store(1L);

    int rating1 = new Random().nextInt(5) + 1;
    int rating2 = new Random().nextInt(5) + 1;
    int rating3 = new Random().nextInt(5) + 1;
    int rating4 = new Random().nextInt(5) + 1;
    int rating5 = new Random().nextInt(5) + 1;
    int rating6 = new Random().nextInt(5) + 1;
    int rating7 = new Random().nextInt(5) + 1;

    Review review1 = new Review(rating1, "eval#1", new User(1L), store);
    Review review2 = new Review(rating2, "eval#2", new User(2L), store);
    Review review3 = new Review(rating3, "eval#3", new User(3L), store);
    Review review4 = new Review(rating4, "eval#4", new User(4L), store);
    Review review5 = new Review(rating5, "eval#5", new User(5L), store);
    Review review6 = new Review(rating6, "eval#6", new User(6L), store);
    Review review7 = new Review(rating7, "eval#7", new User(7L), store);

    @DisplayName("createReview(): ")
    @Test
    void createReview() {
        // given
        setReviews();

        int rating = 5;
        ReviewPost reviewPost = new ReviewPost(rating, "eval#1");
        Double average = expectedAverage(rating);

        given(storeRepository.findStoreAndReview(1L)).willReturn(Optional.of(store));

        // when
        Review result = reviewService.createReview(reviewPost, 1L, 1L);

        // then
        Assertions.assertThat(result.getStore().getRating()).isEqualTo(average);
        verify(storeRepository, times(1)).findStoreAndReview(1L);
    }

    private Double expectedAverage(int rating) {
        return IntStream.of(rating1, rating2, rating3, rating4, rating5, rating6, rating7, rating)
                .average().orElse(0D);
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