package com.wanted.demo.store.repository;

import com.wanted.demo.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findStoreByStoreId(String storeId);

    @Query("select s from Store s left join fetch s.reviews where s.id= :id")
    Optional<Store> findStoreAndReview(Long id);
}
