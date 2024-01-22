package com.yanolja_final.domain.packages.facade;

import com.yanolja_final.domain.packages.dto.response.PackageListItemResponse;
import com.yanolja_final.domain.packages.entity.Hashtag;
import com.yanolja_final.domain.packages.entity.Package;
import com.yanolja_final.domain.packages.service.HashtagService;
import com.yanolja_final.domain.packages.service.PackageService;
import com.yanolja_final.domain.user.entity.User;
import com.yanolja_final.domain.user.service.UserService;
import com.yanolja_final.domain.wish.service.WishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class HashtagFacade {

    private final UserService userService;
    private final PackageService packageService;
    private final HashtagService hashtagService;
    private final WishService wishService;

    @Transactional
    public Page<PackageListItemResponse> getPackagesByHashtagKeyword(
        Long userId,
        String keyword,
        Pageable pageable,
        String sortBy
    ) {
        if ("".equals(keyword)) {
            keyword = null;
        }
        User user = userId == null ? null : userService.findById(userId);
        Hashtag hashtag = hashtagService.getHashtagByKeywordWithIncrementSearchedCount(keyword);
        Page<Package> packagePage = packageService.getPackagesByHashtag(hashtag, sortBy, pageable);
        return packagePage.map(p -> PackageListItemResponse.from(p, wishService.isWish(user, p)));
    }
}
