package com.wanted.demo.store.event;

import com.wanted.demo.store.entity.Store;
import com.wanted.demo.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataCollectEventListener {

    private final StoreRepository storeRepository;

    @Async
    @Transactional
    @EventListener
    public void handleDataCollectEvent(DataCollectEvent event) {
        Optional<Store> optionalStoreByStoreId = storeRepository.findStoreByStoreId(event.store().getStoreId());
        if(optionalStoreByStoreId.isPresent()) {
            Store existsStoreData = optionalStoreByStoreId.get();
            existsStoreData.update(event.store());
        } else {
            storeRepository.save(event.store());
        }
    }
}
