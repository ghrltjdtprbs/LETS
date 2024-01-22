package com.yanolja_final.domain.advertisement.facade;

import com.yanolja_final.domain.advertisement.dto.response.AdDetailResponse;
import com.yanolja_final.domain.advertisement.dto.response.AdListItemResponse;
import com.yanolja_final.domain.advertisement.entity.Advertisement;
import com.yanolja_final.domain.advertisement.service.AdService;
import com.yanolja_final.domain.packages.entity.Continent;
import com.yanolja_final.domain.packages.entity.Nation;
import com.yanolja_final.domain.packages.entity.Package;
import com.yanolja_final.domain.packages.service.PackageService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdFacade {

    private final AdService adService;
    private final PackageService packageService;

    public List<AdListItemResponse> getList() {
        return adService.getList();
    }

    public AdDetailResponse getDetail(Long id) {
        Advertisement ad = adService.findById(id);

        Continent continent = ad.getContinent();
        Nation nation = ad.getNation();
        if ((continent == null) == (nation == null)) {
            throw new IllegalArgumentException("Advertisement는 continent, nation 둘 중 하나만 값이 있어야 함");
        }

        List<Package> packages = new ArrayList<>();
        if (continent != null) {
            packages.addAll(packageService.findAllByContinent(continent));
        }
        if (nation != null) {
            packages.addAll(packageService.findAllByNation(nation));
        }

        Collections.shuffle(packages);
        return AdDetailResponse.from(ad, packages);
    }
}
