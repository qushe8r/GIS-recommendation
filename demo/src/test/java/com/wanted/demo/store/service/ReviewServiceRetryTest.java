package com.wanted.demo.store.service;

import com.wanted.demo.domain.user.UserRepository;
import com.wanted.demo.domain.user.domain.Role;
import com.wanted.demo.domain.user.domain.User;
import com.wanted.demo.global.aop.aspect.RetryAspect;
import com.wanted.demo.store.dto.api_response.StoreRawData;
import com.wanted.demo.store.dto.review.ReviewPost;
import com.wanted.demo.store.entity.Review;
import com.wanted.demo.store.entity.Store;
import com.wanted.demo.store.repository.StoreRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Import(RetryAspect.class)
class ReviewServiceRetryTest {

    @Autowired
    ReviewService reviewService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StoreRepository storeRepository;

    @DisplayName("retry(): @Retry 적용한")
    @Test
    void retry() throws InterruptedException {
        // given
        User user1 = new User(null, "username#1", "password#1", Role.MEMBER, "lat#1", "log#1", false, new ArrayList<>());
        userRepository.save(user1);

        StoreRawData storeRawData = new StoreRawData(null, null, null, "20160826", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        Store store = new Store(storeRawData);
        storeRepository.save(store);

        int rating1 = new Random().nextInt(5) + 1;
        int rating2 = new Random().nextInt(5) + 1;
        int rating3 = new Random().nextInt(5) + 1;
        int rating4 = new Random().nextInt(5) + 1;
        int rating5 = new Random().nextInt(5) + 1;

        double average = IntStream.of(rating1, rating2, rating3, rating4, rating5).average().orElse(0D);

        ReviewPost reviewPost1 = new ReviewPost(rating1, "eval#1");
        ReviewPost reviewPost2 = new ReviewPost(rating2, "eval#2");
        ReviewPost reviewPost3 = new ReviewPost(rating3, "eval#3");
        ReviewPost reviewPost4 = new ReviewPost(rating4, "eval#4");
        ReviewPost reviewPost5 = new ReviewPost(rating5, "eval#5");

        ReviewPost[] reviewPosts = new ReviewPost[]{reviewPost1, reviewPost2, reviewPost3, reviewPost4, reviewPost5};

        final int numberOfThreads = 5;
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(numberOfThreads);

        // 랜덤 값 확인용
        System.out.println("reviewPosts = " + Arrays.toString(reviewPosts));

        for (ReviewPost post : reviewPosts) {
            executorService.execute(() -> {
                try {
                    Review review = reviewService.createReview(post, 1L, 1L);
                    System.out.println("rating = " + review.getStore().getRating());
                    System.out.println("post.rating = " + post.rating());
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                latch.countDown();
            });
        }
        latch.await();

        // when
        Store result = storeRepository.findById(1L).orElseThrow();

        // then
        Assertions.assertThat(result)
                .hasFieldOrPropertyWithValue("rating", average)
                .hasFieldOrPropertyWithValue("version", numberOfThreads);
    }
}
