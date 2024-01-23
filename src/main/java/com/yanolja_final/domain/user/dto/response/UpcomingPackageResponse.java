package com.yanolja_final.domain.user.dto.response;

import com.yanolja_final.domain.order.entity.Order;
import com.yanolja_final.domain.packages.entity.PackageDepartureOption;
import com.yanolja_final.domain.user.entity.User;

public record UpcomingPackageResponse(
    Long packageId,
    String imageUrl,
    String title,
    Long dday,
    String nationName,
    String departureDate,
    String endDate
) {
  public static UpcomingPackageResponse from(User user, Order order) {
      PackageDepartureOption packageDepartureOption = order.preventPassedDepartureDate();
      Long dday = packageDepartureOption.calculateDday();
      String departureDate = packageDepartureOption.formattedDepartureDate();
      String endDate = packageDepartureOption.formattedEndDate();

      return new UpcomingPackageResponse(
          order.getAPackage().getId(),
          order.getAPackage().getThumbnailImageUrl(),
          order.getAPackage().getTitle(),
          dday,
          order.getAPackage().getNationName(),
          departureDate,
          endDate
      );
  }

  public static UpcomingPackageResponse emptyResponse() {
      return new UpcomingPackageResponse(
          null,
          null,
          null,
          null,
          null,
          null,
          null
      );
  }
}
