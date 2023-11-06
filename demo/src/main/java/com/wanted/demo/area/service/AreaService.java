package com.wanted.demo.area.service;

import com.wanted.demo.area.entity.Area;
import com.wanted.demo.area.repository.AreaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class AreaService {

    @Value("${area-info.file-path}")
    private String filePath;

    private final AreaRepository areaRepository;

    @Transactional
    public void saveAreaInfoCsvToDb() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String row;
            while ((row = br.readLine()) != null) {
                try {
                    String[] split = row.split(",");
                    Area area = new Area(split[0], split[1], Double.valueOf(split[2]), Double.valueOf(split[3]));
                    areaRepository.save(area);
                } catch (Exception e) {
                    log.error("csv file invalid={}", e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
