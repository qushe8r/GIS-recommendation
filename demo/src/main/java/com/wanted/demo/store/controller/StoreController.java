package com.wanted.demo.store.controller;

import com.wanted.demo.store.dto.StoreListDTO;
import com.wanted.demo.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StoreController {
    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/storesInRange")
    @ResponseBody
    public List<StoreListDTO> getStoresInRange( @RequestParam("lat") double lat, @RequestParam("lon") double lon, @RequestParam("range") double range ) {
        return storeService.findStoresInRange(lat,lon,range);
    }
}
