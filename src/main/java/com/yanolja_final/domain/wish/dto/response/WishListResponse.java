package com.yanolja_final.domain.wish.dto.response;

import com.yanolja_final.domain.packages.entity.Hashtag;
import com.yanolja_final.domain.packages.entity.Package;
import com.yanolja_final.domain.wish.entity.Wish;
import java.util.List;
import java.util.stream.Collectors;

public record WishListResponse(
    Long packageId,
    String imageUrl,
    String nationName,
    String title,
    List<String> hashtags,
    Integer minPrice,
    Integer lodgeDays,
    Integer tripDays) {

    public WishListResponse(Wish wish) {
        this(
            wish.getAPackage().getId(),
            wish.getAPackage().getThumbnailImageUrl(),
            wish.getAPackage().getNationName(),
            wish.getAPackage().getTitle(),
            wish.getAPackage().getHashtags().stream().map(Hashtag::getName).collect(Collectors.toList()),
            wish.getAPackage().getMinPrice(),
            wish.getAPackage().getLodgeDays(),
            wish.getAPackage().getTripDays()
        );
    }
}
