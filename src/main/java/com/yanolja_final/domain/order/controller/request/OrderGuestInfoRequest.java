package com.yanolja_final.domain.order.controller.request;

import jakarta.validation.constraints.NotNull;

public record OrderGuestInfoRequest(
    @NotNull
    Integer adult,
    @NotNull
    Integer infant,
    @NotNull
    Integer baby
) {

}
