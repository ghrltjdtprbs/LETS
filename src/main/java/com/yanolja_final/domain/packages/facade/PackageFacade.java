package com.yanolja_final.domain.packages.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanolja_final.domain.packages.dto.response.PackageAvailableDateResponse;
import com.yanolja_final.domain.packages.dto.response.PackageCompareResponse;
import com.yanolja_final.domain.packages.dto.response.PackageScheduleResponse;
import com.yanolja_final.domain.packages.dto.response.PackageDetailResponse;
import com.yanolja_final.domain.packages.dto.response.PackageListItemResponse;
import com.yanolja_final.domain.packages.entity.Hashtag;
import com.yanolja_final.domain.packages.dto.response.PackageSummaryResponse;
import com.yanolja_final.domain.packages.entity.Package;
import com.yanolja_final.domain.packages.entity.PackageDepartureOption;
import com.yanolja_final.domain.packages.service.PackageService;
import com.yanolja_final.domain.review.entity.Review;
import com.yanolja_final.domain.review.service.ReviewService;
import com.yanolja_final.domain.user.entity.User;
import com.yanolja_final.domain.user.service.UserService;
import com.yanolja_final.domain.wish.service.WishService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

        return PackageDetailResponse.from(aPackage, departOption, user, isWish, averageStars, reviewCount);
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
        List<String> schedules = scheduleResponses.stream()
            .map(PackageScheduleResponse::schedule)
            .flatMap(List::stream)
            .collect(Collectors.toList());

        List<Review> reviews = reviewService.findReviewsByPackageId(packageId);
        int reviewCount = reviews.size();
        int scoreSum = reviews.stream().mapToInt(Review::getTotalScore).sum();
        double averageStars = reviewCount == 0 ? 0.0 : scoreSum / (double) reviewCount / 4;

        return PackageSummaryResponse.from(
            aPackage, departureOption, reviewCount, averageStars, schedules
        );
    }

    public Page<PackageListItemResponse> getSimilarPackages(
        Pageable pageable,
        Long packageId,
        Long userId
    ) {
        User user = userId == null ? null : userService.findById(userId);
        Package basePackage = packageService.findById(packageId);
        List<Package> packages = packageService.findAll();

        List<Package> similarPackages = getSimilarPackages(packages, basePackage);

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), similarPackages.size());
        List<Package> sublist = similarPackages.subList(start, end);

        return new PageImpl<>(sublist, pageable, sublist.size())
            .map(p -> PackageListItemResponse.from(p, wishService.isWish(user, p)));
    }

    private List<Package> getSimilarPackages(List<Package> packages, Package basePackage) {
        boolean existsBySameNation = packages.stream()
            .anyMatch(p -> p.getNationName().equals(basePackage.getNationName()));
        if (existsBySameNation) {
            return packages.stream()
                .filter(p -> !Objects.equals(p.getId(), basePackage.getId()))
                .filter(p -> p.getNationName().equals(basePackage.getNationName()))
                .sorted(Comparator.comparingLong(basePackage::getCommonHashtagsCount).reversed())
                .collect(Collectors.toList());
        }

        return packages.stream()
            .sorted(Comparator.comparingInt(Package::getMonthlyPurchasedCount).reversed())
            .collect(Collectors.toList());
    }
}
