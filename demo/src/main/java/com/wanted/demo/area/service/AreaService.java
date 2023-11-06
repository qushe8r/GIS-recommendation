package com.wanted.demo.area.service;

import com.wanted.demo.area.dto.AreaDTO;
import com.wanted.demo.area.entity.Area;
import com.wanted.demo.area.repository.AreaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<AreaDTO> getAreaList() {
        List<Area> areas = areaRepository.findAll();
        List<AreaDTO> areaList = new ArrayList<>();

        for (Area area : areas) {
            AreaDTO areaDTO = new AreaDTO(
                    area.getId(),
                    area.getDoSi(),
                    area.getSgg()
            );
            areaList.add(areaDTO);
        }
        return areaList;
    }

    public AreaDTO getAreaDetails(Long id) {
        Optional<Area> areaOptional = areaRepository.findById(id);

        if (areaOptional.isPresent()) {
            Area area = areaOptional.get();
            return new AreaDTO(
                    area.getId(),
                    area.getDoSi(),
                    area.getSgg(),
                    area.getLongitude(),
                    area.getLatitude()
            );
        } else {
            throw new EntityNotFoundException("지역id가 존재하지 않습니다. id : " + id);
        }
    }
}
