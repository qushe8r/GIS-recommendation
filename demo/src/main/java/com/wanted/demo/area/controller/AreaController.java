package com.wanted.demo.area.controller;

import com.wanted.demo.area.dto.AreaDTO;
import com.wanted.demo.area.service.AreaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/area")
public class AreaController {

    private final AreaService areaService;

    @PostMapping("/info-data")
    public void saveAreaInfoCsv() {
        areaService.saveAreaInfoCsvToDb();
    }

    @GetMapping("/list")
    public List<AreaDTO> getAreaList() {
        return areaService.getAreaList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaDTO> getAreaDetails(@PathVariable Long id) {
        AreaDTO areaDetails = areaService.getAreaDetails(id);
        return ResponseEntity.ok(areaDetails);
    }
}
