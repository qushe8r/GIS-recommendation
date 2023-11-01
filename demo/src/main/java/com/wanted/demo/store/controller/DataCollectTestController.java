package com.wanted.demo.store.controller;

import com.wanted.demo.store.service.PublicDataCollectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class DataCollectTestController {

    private final PublicDataCollectService publicDataCollectService;

    @PostMapping("/test/data/jpn/collect")
    public ResponseEntity<?> collectPublicJpnDataForDev() {
        publicDataCollectService.collectJpnStorePublicData();
        return ResponseEntity.ok().body("ok");
    }

    @PostMapping("/test/data/kim-bob/collect")
    public ResponseEntity<?> collectPublicKimBobDataForDev() {
        publicDataCollectService.collectKimBobStorePublicData();
        return ResponseEntity.ok().body("ok");
    }

    @PostMapping("/test/data/chi/collect")
    public ResponseEntity<?> collectPublicChiDataForDev() {
        publicDataCollectService.collectChiStorePublicData();
        return ResponseEntity.ok().body("ok");
    }
}