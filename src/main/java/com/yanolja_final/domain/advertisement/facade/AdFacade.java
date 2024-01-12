package com.yanolja_final.domain.advertisement.facade;

import com.yanolja_final.domain.advertisement.dto.response.AdListItemResponse;
import com.yanolja_final.domain.advertisement.service.AdService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdFacade {

    private final AdService adService;

    public List<AdListItemResponse> getList() {
        return adService.getList();
    }
}
