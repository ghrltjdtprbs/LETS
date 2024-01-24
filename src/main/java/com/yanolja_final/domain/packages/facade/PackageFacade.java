package com.yanolja_final.domain.packages.facade;

import com.yanolja_final.domain.packages.dto.response.PackageAvailableDateResponse;
import com.yanolja_final.domain.packages.dto.response.PackageCompareResponse;
import com.yanolja_final.domain.packages.dto.response.PackageDetailResponse;
import com.yanolja_final.domain.packages.dto.response.PackageListItemResponse;
import com.yanolja_final.domain.packages.dto.response.PackageScheduleResponse;
import com.yanolja_final.domain.packages.dto.response.PackageSummaryResponse;
import com.yanolja_final.domain.packages.entity.Package;
import com.yanolja_final.domain.packages.entity.PackageDepartureOption;
import com.yanolja_final.domain.packages.service.PackageService;
import com.yanolja_final.domain.review.entity.Review;
import com.yanolja_final.domain.review.service.ReviewService;
import com.yanolja_final.domain.user.entity.User;
import com.yanolja_final.domain.user.service.UserService;
import com.yanolja_final.domain.wish.service.WishService;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
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
        packageService.viewed(aPackage);

        return PackageDetailResponse.from(aPackage, departOption, user, isWish, averageStars,
            reviewCount);
    }

    public Page<PackageListItemResponse> getAllList(Pageable pageable, Long userId) {
        User user = userId == null ? null : userService.findById(userId);
        Page<Package> packages = packageService.findAll(pageable);
        return packages.map(p -> PackageListItemResponse.from(p, wishService.isWish(user, p)));
    }

    public List<PackageScheduleResponse> getSchedules(Long packageId) {
        return packageService.getSchedulesById(packageId);
    }

    public List<PackageAvailableDateResponse> getAvailableDates(Long packageId) {
        Package aPackage = packageService.findById(packageId);
        List<PackageDepartureOption> availableDates = aPackage.getAvailableDates();
        return availableDates.stream()
            .filter(PackageDepartureOption::isNotExpired)
            .sorted(Comparator.comparing(PackageDepartureOption::getDepartureDate))
            .map(PackageAvailableDateResponse::from)
            .collect(Collectors.toList());
    }

    public Page<PackageListItemResponse> getTopViews(Pageable pageable, Long userId) {
        User user = userId == null ? null : userService.findById(userId);
        Page<Package> packages = packageService.findAllByViewedCount(pageable);
        return packages.map(p -> PackageListItemResponse.from(p, wishService.isWish(user, p)));
    }

    public Page<PackageListItemResponse> getTopPurchases(Pageable pageable, Long userId) {
        User user = userId == null ? null : userService.findById(userId);
        Page<Package> packages = packageService.findAllByPurchasedCount(pageable);
        return packages.map(p -> PackageListItemResponse.from(p, wishService.isWish(user, p)));
    }

    public PackageCompareResponse compare(Long fixedPackageId, Long comparePackageId) {
        PackageSummaryResponse fixedPackage = getSummary(fixedPackageId);
        PackageSummaryResponse comparePackage = getSummary(comparePackageId);

        return new PackageCompareResponse(fixedPackage, comparePackage);
    }

    private PackageSummaryResponse getSummary(Long packageId) {
        Package aPackage = packageService.findById(packageId);
        PackageDepartureOption departureOption = aPackage.getAvailableDate(null);

        List<PackageScheduleResponse> scheduleResponses = packageService.getSchedulesById(
            packageId);

        List<Review> reviews = reviewService.findReviewsByPackageId(packageId);
        int reviewCount = reviews.size();
        int scoreSum = reviews.stream().mapToInt(Review::getTotalScore).sum();
        double averageStars = reviewCount == 0 ? 0.0 : scoreSum / (double) reviewCount / 4;

        return PackageSummaryResponse.from(
            aPackage, departureOption, reviewCount, averageStars, scheduleResponses
        );
    }

    public Page<PackageListItemResponse> getSimilarPackages(
        Pageable pageable,
        Long packageId,
        Long userId
    ) {
        User user = userId == null ? null : userService.findById(userId);
        Package basePackage = packageService.findById(packageId);

        Page<Package> similarPackages = packageService.findSimilarPackages(basePackage, pageable);
        if (similarPackages.isEmpty()) {
            Page<Package> sortedByPurchased = packageService.findAllByPurchasedCount(pageable);
            return sortedByPurchased.map(
                p -> PackageListItemResponse.from(p, wishService.isWish(user, p)));
        }

        return similarPackages.map(
            p -> PackageListItemResponse.from(p, wishService.isWish(user, p)));
    }
}
