package com.wanted.demo.store.controller;

import com.wanted.demo.store.dto.StoreDetailDTO;
import com.wanted.demo.store.dto.StoreListDTO;
import com.wanted.demo.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/store")
public class StoreController {
    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/recom")
    @ResponseBody
    public List<StoreListDTO> getStoresInRange(
            @RequestParam("lat") double lat,
            @RequestParam("lon") double lon,
            @RequestParam("range") double range,
            @RequestParam(name="sort", required = false, defaultValue = "distance") String sort) {
        return storeService.findStoresInRange(lat,lon,range,sort);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreDetailDTO> getStoreDetails(@PathVariable Long id) {
        StoreDetailDTO storeDetailDTO = storeService.getStoreDetails(id);
        return ResponseEntity.ok(storeDetailDTO);
    }
}
