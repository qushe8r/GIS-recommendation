package com.wanted.demo.store.service;

import com.wanted.demo.store.dto.ReviewDTO;
import com.wanted.demo.store.dto.StoreDetailDTO;
import com.wanted.demo.store.dto.StoreListDTO;
import com.wanted.demo.store.entity.Review;
import com.wanted.demo.store.entity.Store;
import com.wanted.demo.store.repository.StoreRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StoreService {
    private final StoreRepository storeRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public List<StoreListDTO> findStoresInRange(double lat, double lon, double range, String sort) {
        List<Store> stores = storeRepository.findAll();

        List<StoreListDTO> storesInRange = new ArrayList<>();

        for (Store store : stores) {
            double storeLat = store.getRefineWgs84Lat();
            double storeLon = store.getRefineWgs84Logt();

            double distance = latLonToKm(storeLat, storeLon, lat, lon);

            if (distance <= range) {
                StoreListDTO storeListDTO = new StoreListDTO(
                        store.getId(),
                        store.getStoreId(),
                        store.getBizplcNm(),
                        store.getRating(),
                        storeLat,
                        storeLon,
                        distance
                );
                storesInRange.add(storeListDTO);
            }
        }

        switch (sort) {
            case "distance":
                storesInRange.sort(Comparator.comparing(StoreListDTO::getDistance));
                break;
            case "rating":
                storesInRange.sort(Comparator.comparing(StoreListDTO::getRating).reversed());
                break;
            default:
                storesInRange.sort(Comparator.comparing(StoreListDTO::getDistance));
        }

        return storesInRange;
    }

    private double latLonToKm(double storeLat, double storeLon, double lat2, double lon2) {
        double radius = 6371; //(km)

        double dLat = Math.toRadians(lat2 - storeLat);
        double dLon = Math.toRadians(lon2 - storeLon);

        storeLat = Math.toRadians(storeLat);
        lat2 = Math.toRadians(lat2);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(storeLat) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return radius * c;
    }

    public StoreDetailDTO getStoreDetails(Long id) {
        Optional<Store> storeOptional = storeRepository.findById(id);

        if (storeOptional.isPresent()) {
            Store store = storeOptional.get();
            List<ReviewDTO> reviewDTOs = new ArrayList<>();

            for(Review review : store.getReviews()) {
                ReviewDTO reviewDTO = new ReviewDTO(
                        review.getId(),
                        review.getRating(),
                        review.getEvaluation(),
                        review.getUser().getUsername()
                );
                reviewDTOs.add(reviewDTO);
            }

            return new StoreDetailDTO(
                    store.getId(),
                    store.getStoreId(),
                    store.getBizplcNm(),
                    store.getLicensgDe(),
                    store.getBsnStateNm(),
                    store.getClsbizDe(),
                    store.getLocplcAr(),
                    store.getGradFacltDivNm(),
                    store.getMaleEnflpsnCnt(),
                    store.getYy(),
                    store.getMultiUseBizestblYn(),
                    store.getGradDivNm(),
                    store.getTotFacltScale(),
                    store.getFemaleEnflpsnCnt(),
                    store.getBsnsiteCircumfrDivNm(),
                    store.getSanittnIndutypeNm(),
                    store.getSanittnBizcondNm(),
                    store.getTotEmplyCnt(),
                    store.getRefineRoadnmAddr(),
                    store.getRefineLotnoAddr(),
                    store.getRefineZipCd(),
                    store.getRefineWgs84Lat(),
                    store.getRefineWgs84Logt(),
                    store.getRating(),
                    reviewDTOs
            );
        } else {
            throw new EntityNotFoundException("Store를 찾을 수 없습니다.");
        }
    }

}
