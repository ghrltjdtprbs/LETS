package com.yanolja_final.domain.search.facade;

import com.yanolja_final.domain.packages.entity.Continent;
import com.yanolja_final.domain.packages.entity.Nation;
import com.yanolja_final.domain.packages.service.ContinentService;
import com.yanolja_final.domain.packages.service.HashtagService;
import com.yanolja_final.domain.packages.service.NationService;
import com.yanolja_final.domain.packages.service.PackageService;
import com.yanolja_final.domain.search.controller.response.ContinentNationResponse;
import com.yanolja_final.domain.search.controller.response.HashTagNamesResponse;
import com.yanolja_final.domain.search.controller.response.HashtagResponse;
import com.yanolja_final.domain.search.controller.response.SearchedPackageCountResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchFacade {

    private final HashtagService hashtagService;
    private final NationService nationService;
    private final ContinentService continentService;
    private final PackageService packageService;

    public List<HashtagResponse> getHashtags() {
        return hashtagService.getAllHashtagInfo();
    }

    public List<ContinentNationResponse> getAllContinentAndNationInfo() {
        List<Nation> nations = nationService.getAllNationInfo();
        List<Continent> continents = continentService.getAllContinentInfo();
        return List.of(ContinentNationResponse.from(nations, continents));
    }

    public HashTagNamesResponse findAllByOrderBySearchedCountDesc() {
        return HashTagNamesResponse.from(hashtagService.findAllByOrderBySearchedCountDesc());
    }

    public SearchedPackageCountResponse getFilteredPackageCount(int minPrice, int maxPrice,
        String hashtags, String nations, String continents) {
        return packageService.getFilteredPackageCount(minPrice, maxPrice, hashtags, nations,
            continents);
    }
}
