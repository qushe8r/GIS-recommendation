package com.wanted.demo.area.controller;

import com.wanted.demo.area.service.AreaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AreaController {

    private final AreaService areaService;

    @PostMapping("/api/area/info-data")
    public void saveAreaInfoCsv() {
        areaService.saveAreaInfoCsvToDb();
    }
}
