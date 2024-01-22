package com.yanolja_final.domain.search.facade;

import com.yanolja_final.domain.packages.dto.response.PackageListItemResponse;
import com.yanolja_final.domain.packages.entity.Continent;
import com.yanolja_final.domain.packages.entity.Hashtag;
import com.yanolja_final.domain.packages.entity.Nation;
import com.yanolja_final.domain.packages.entity.Package;
import com.yanolja_final.domain.packages.service.ContinentService;
import com.yanolja_final.domain.packages.service.HashtagService;
import com.yanolja_final.domain.packages.service.NationService;
import com.yanolja_final.domain.packages.service.PackageService;
import com.yanolja_final.domain.search.controller.response.ContinentNationResponse;
import com.yanolja_final.domain.search.controller.response.HashTagNamesResponse;
import com.yanolja_final.domain.search.controller.response.HashtagResponse;
import com.yanolja_final.domain.search.controller.response.SearchedPackageCountResponse;
import com.yanolja_final.domain.user.entity.User;
import com.yanolja_final.domain.user.service.UserService;
import com.yanolja_final.domain.wish.service.WishService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchFacade {

    private final UserService userService;
    private final HashtagService hashtagService;
    private final NationService nationService;
    private final ContinentService continentService;
    private final PackageService packageService;
    private final WishService wishService;

    public List<HashtagResponse> getHashtags() {
        return hashtagService.getAllHashtagInfo();
    }

    public List<ContinentNationResponse> getAllContinentAndPopularNationInfo() {
        List<Nation> nations = nationService.getPopularNationInfo();
        List<Continent> continents = continentService.getAllContinentInfo();
        return List.of(ContinentNationResponse.from(nations, continents));
    }

    public HashTagNamesResponse findAllByOrderBySearchedCountDesc() {
        return HashTagNamesResponse.from(hashtagService.findAllByOrderBySearchedCountDesc());
    }

    @Transactional
    public Page<PackageListItemResponse> getFilteredPackage(
        Long userId,
        int minPrice,
        int maxPrice,
        String hashtagsString,
        String nations,
        String continents,
        String sortBy,
        Pageable pageable
    ) {
        User user = userId == null ? null : userService.findById(userId);
        List<Hashtag> hashtags =
            hashtagService.getHashtagWithIncrementSearchedCount(hashtagsString);
        Page<Package> packages = packageService.getFilteredPackage(
            minPrice, maxPrice, hashtags, nations, continents, sortBy, pageable
        );
        return packages.map(p -> PackageListItemResponse.from(p, wishService.isWish(user, p)));
    }

    public SearchedPackageCountResponse getFilteredPackageCount(int minPrice, int maxPrice,
        String hashtags, String nations, String continents) {
        return packageService.getFilteredPackageCount(minPrice, maxPrice, hashtags, nations,
            continents);
    }
}
