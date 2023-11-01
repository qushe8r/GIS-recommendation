package com.wanted.demo.store.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.demo.store.dto.api_response.ChiStoreApiResponse;
import com.wanted.demo.store.dto.api_response.JpnStoreApiResponse;
import com.wanted.demo.store.dto.api_response.KimBobStoreApiResponse;
import com.wanted.demo.store.entity.Store;
import com.wanted.demo.store.event.DataCollectEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Slf4j
@RequiredArgsConstructor
@Service
public class PublicDataCollectService {

    private final ApplicationEventPublisher eventPublisher;

    @Value("${open.api.key}")
    private String key;

    @Value("${open.api.baseurl}")
    private String baseUrl;

    @Value("${open.api.jpn-path}")
    private String jpnPath;

    @Value("${open.api.kim-bob-path}")
    private String kimBobPath;

    @Value("${open.api.chi-path}")
    private String chiPath;

    public void collectJpnStorePublicData() {
        for (int i = 1; i <= 118; i++) {
            collectData(i, jpnPath, JpnStoreApiResponse.class);
        }
    }

    public void collectKimBobStorePublicData() {
        for (int i = 1; i <= 19; i++) {
            collectData(i, kimBobPath, KimBobStoreApiResponse.class);
        }
    }

    public void collectChiStorePublicData() {
        for (int i = 1; i <= 145; i++) {
            collectData(i, chiPath, ChiStoreApiResponse.class);
        }
    }

    private <T> void collectData(int page, String path, Class<T> responseType) {
        Flux<String> rawDataFlux = WebClient.builder()
                .baseUrl(baseUrl).build()
                .get()
                .uri(uriBuilder -> uriBuilder.path(path)
                        .queryParam("KEY", key)
                        .queryParam("Type", "json")
                        .queryParam("pIndex", page)
                        .queryParam("pSize", 100)
                        .build())
                .retrieve()
                .bodyToFlux(String.class);

        Flux<T> apiResponseFlux = rawDataFlux.mapNotNull(jsonString -> {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.readValue(jsonString, responseType);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        });

        apiResponseFlux.flatMap(apiResponse -> {
                    if (responseType == JpnStoreApiResponse.class) {
                        return Flux.fromIterable(((JpnStoreApiResponse) apiResponse).genrestrtJpnFood());
                    }
                    if (responseType == KimBobStoreApiResponse.class) {
                        return Flux.fromIterable(((KimBobStoreApiResponse) apiResponse).genrestrtLunch());
                    }
                    if (responseType == ChiStoreApiResponse.class) {
                        return Flux.fromIterable(((ChiStoreApiResponse) apiResponse).genrestrtChiFood());
                    }
                    return Flux.empty();
                })
                .flatMap(gf -> {
                    if (gf != null && gf.row() != null) {
                        return Flux.fromIterable(gf.row());
                    } else {
                        return Flux.empty();
                    }
                })
                .map(Store::new)
                .doOnNext(store -> eventPublisher.publishEvent(new DataCollectEvent(store)))
                .subscribe();
    }
}