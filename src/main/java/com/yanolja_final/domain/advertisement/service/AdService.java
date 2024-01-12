package com.yanolja_final.domain.advertisement.service;

import com.yanolja_final.domain.advertisement.dto.response.AdListItemResponse;
import com.yanolja_final.domain.advertisement.entity.Advertisement;
import com.yanolja_final.domain.advertisement.repository.AdRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdService {

    private final AdRepository adRepository;

    public List<AdListItemResponse> getList() {
        List<Advertisement> ads = adRepository.findAll();
        return ads.stream()
            .map(AdListItemResponse::from)
            .collect(Collectors.toList());
    }
}
