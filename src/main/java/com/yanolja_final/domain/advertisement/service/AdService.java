package com.yanolja_final.domain.advertisement.service;

import com.yanolja_final.domain.advertisement.dto.response.AdDetailResponse;
import com.yanolja_final.domain.advertisement.dto.response.AdListItemResponse;
import com.yanolja_final.domain.advertisement.entity.Advertisement;
import com.yanolja_final.domain.advertisement.exception.AdvertisementNotFound;
import com.yanolja_final.domain.advertisement.repository.AdRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdService {

    private final AdRepository adRepository;

    public List<AdListItemResponse> getList() {
        List<Advertisement> ads = adRepository.findAll();
        return ads.stream()
            .map(AdListItemResponse::from)
            .collect(Collectors.toList());
    }

    public AdDetailResponse getDetail(Long id) {
        Advertisement ad = adRepository.findById(id).orElseThrow(AdvertisementNotFound::new);
        return AdDetailResponse.from(ad);
    }
}
