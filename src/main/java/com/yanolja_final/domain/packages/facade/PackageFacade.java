package com.yanolja_final.domain.packages.facade;

import com.yanolja_final.domain.packages.dto.response.PackageDetailResponse;
import com.yanolja_final.domain.packages.entity.Package;
import com.yanolja_final.domain.packages.entity.PackageDepartureOption;
import com.yanolja_final.domain.packages.service.PackageService;
import com.yanolja_final.domain.review.entity.Review;
import com.yanolja_final.domain.review.service.ReviewService;
import com.yanolja_final.domain.user.entity.User;
import com.yanolja_final.domain.user.service.UserService;
import com.yanolja_final.domain.wish.service.WishService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PackageFacade {

    private final PackageService packageService;
    private final ReviewService reviewService;
    private final WishService wishService;
    private final UserService userService;

    public PackageDetailResponse getDetail(Long packageId, Long userId, String departDate) {
        Package aPackage = packageService.findById(packageId);
        User user = userId == null ? null : userService.findById(userId);
        boolean isWish = user != null && wishService.isWish(user, aPackage);

        List<Review> reviews = reviewService.findReviewsByPackageId(packageId);
        int reviewCount = reviews.size();
        int scoreSum = reviews.stream().mapToInt(Review::getTotalScore).sum();
        double averageStars = reviewCount == 0 ? 0.0 : scoreSum / (double) reviewCount / 4;

        PackageDepartureOption departOption = aPackage.getAvailableDate(departDate);

        return PackageDetailResponse.from(aPackage, departOption, user, isWish, averageStars, reviewCount);
    }
}
