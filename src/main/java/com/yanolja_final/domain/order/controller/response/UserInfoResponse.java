package com.yanolja_final.domain.order.controller.response;

import com.yanolja_final.domain.user.entity.User;

public record UserInfoResponse(
    String username,
    String email
) {

    public static UserInfoResponse fromEntity(User user) {
        return new UserInfoResponse(
            user.getUsername(),
            user.getEmail()
        );
    }
}
