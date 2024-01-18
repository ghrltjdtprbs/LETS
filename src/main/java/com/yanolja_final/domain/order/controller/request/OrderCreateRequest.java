package com.yanolja_final.domain.order.controller.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanolja_final.domain.order.dto.OrderDetailInfoDTO;
import com.yanolja_final.domain.order.entity.Order;
import com.yanolja_final.domain.packages.entity.Package;
import com.yanolja_final.domain.packages.entity.PackageDepartureOption;
import com.yanolja_final.domain.user.entity.User;
import jakarta.validation.constraints.NotNull;

public record OrderCreateRequest(
    @NotNull
    Long packageId,
    @NotNull
    Long availableDateId,
    String requestMessage,
    @NotNull
    Boolean cancelFeeAgreement,
    @NotNull
    OrderGuestInfoRequest numberOfPeople
) {

    public Order toEntity(User user, Package aPackage,
        PackageDepartureOption packageDepartureOption, String code) {
        return Order.builder()
            .user(user)
            .aPackage(aPackage)
            .availableDateId(availableDateId)
            .code(code)
            .detailInfo(createOrderDetailInfo())
            .totalPrice(calcTotalPrice(packageDepartureOption.getAdultPrice(),
                packageDepartureOption.getInfantPrice(), packageDepartureOption.getBabyPrice()))
            .build();
    }

    private String createOrderDetailInfo() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            OrderDetailInfoDTO details =
                new OrderDetailInfoDTO(requestMessage, cancelFeeAgreement, numberOfPeople);
            return objectMapper.writeValueAsString(details);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public Integer totalCount() {
        return numberOfPeople.adult() + numberOfPeople.baby() + numberOfPeople.infant();
    }

    private Integer calcTotalPrice(int adultPrice, int infantPrice, int babyPrice) {
        return (numberOfPeople().adult() * adultPrice)
            + (numberOfPeople().infant() * infantPrice)
            + (numberOfPeople().baby() * babyPrice);
    }
}
