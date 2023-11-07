package com.wanted.demo.store.service;

import com.wanted.demo.global.aop.annotation.Retry;
import com.wanted.demo.store.dto.review.ReviewPost;
import com.wanted.demo.store.entity.Review;
import com.wanted.demo.store.entity.Store;
import com.wanted.demo.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final StoreRepository storeRepository;

    @Retry
    @Transactional
    public Review createReview(ReviewPost post, Long userId, Long storeId) {
        // TODO: user: principal에서 꺼내오거나 검증하기
        Store store = storeRepository.findStoreAndReview(storeId)
                .orElseThrow(() -> new IllegalArgumentException("store not found"));

        Review review = post.toEntity(userId);
        store.receives(review);
        store.calculateRating();

        // store의 rating이 계산 이후에 변화가 없어서 dirty checking이 이루어지지 않는 것을 방지
        store.versioning();

        return review;
    }
}
